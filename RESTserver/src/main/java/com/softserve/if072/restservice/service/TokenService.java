package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;
import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@PropertySource(value = {"classpath:security.properties"})
public class TokenService {

    private static final Logger LOGGER = LogManager.getLogger(TokenService.class);

    private final String SECURITY_KEY;
    private final String MESSAGE_DIGEST_ALGORITHM;
    private final String TOKEN_DELIMITER;
    private final int TOKEN_VALIDITY_TIME;

    private Environment environment;
    private UserDAO userDAO;
    private MessageDigest messageDigest;
    private HexBinaryAdapter hexBinaryAdapter;

    @Autowired
    public TokenService(Environment environment, UserDAO userDAO) {
        this.environment = environment;
        this.userDAO = userDAO;
        this.hexBinaryAdapter = new HexBinaryAdapter();
        this.SECURITY_KEY = environment.getProperty("security.tokenEncryptionKey");
        this.MESSAGE_DIGEST_ALGORITHM = environment.getProperty("security.messageDigestAlgorithm");
        this.TOKEN_DELIMITER = environment.getProperty("security.tokenDelimiter");
        this.TOKEN_VALIDITY_TIME = Integer.parseInt(environment.getProperty("security.tokenValidityTimeInSeconds"));
        try {
            this.messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't find message digest instance for " + MESSAGE_DIGEST_ALGORITHM, e);
        }
    }

    public void validate(CustomAuthenticationToken token) {
        String[] tokenParts = decodeToken(token.getToken());
        if (tokenParts != null) {
            token.setUserName(tokenParts[0]);

            try {
                token.setExpirateinDate(Long.parseLong(tokenParts[1]));
            } catch (NumberFormatException e) {
                LOGGER.warn("Can't parse token expiration data. Date format is incorrect");
                return;
            }

            if (new DateTime(token.getExpirateinDate()).isBeforeNow()) {
                LOGGER.info("Token's validity time expired, new token must be generated");
                return;
            }

            token.setConfirmationKey(tokenParts[2]);
            String expectedKey = buildTokenConfirmationKey(token.getUserName(), token.getExpirateinDate());
            if (!expectedKey.equals(token.getConfirmationKey())) {
                LOGGER.warn("Received key does not match the expected one: " + token.getConfirmationKey() + " <> " + expectedKey);
                return;
            }

            //if there is no errors, set token as valid
            token.setValid(true);
        }
    }

    public User getUserByToken(CustomAuthenticationToken token) {
        return userDAO.getByUsername(token.getUserName());
    }

    public String generateTokenFor(String username) {
        long expirationDate = DateTime.now().plusSeconds(TOKEN_VALIDITY_TIME).getMillis();
        String tokenString = buildToken(username, expirationDate);
        return new String(Base64.encodeBase64(tokenString.getBytes()));
    }

    private String[] decodeToken(String tokenString) {
        if (isBlank(tokenString) || !Base64.isBase64(tokenString)) {
            return null;
        }

        String token = new String(Base64.decodeBase64(tokenString));
        String[] tokenParts = token.split(TOKEN_DELIMITER);
        if (tokenParts.length != 3) {
            return null;
        }

        for (String tokenPart : tokenParts) {
            if (tokenPart.length() == 0)
                return null;
        }

        return tokenParts;
    }

    private String buildToken(String username, long expirationDate) {
        String tokenConfirmationKey = buildTokenConfirmationKey(username, expirationDate);
        String token = new StringBuilder()
                .append(username)
                .append(TOKEN_DELIMITER)
                .append(expirationDate)
                .append(TOKEN_DELIMITER)
                .append(tokenConfirmationKey)
                .toString();

        return token;
    }

    private String buildTokenConfirmationKey(String username, long expirationDate) {
        StringBuilder tokenBuilder = new StringBuilder();
        String token = tokenBuilder
                .append(username)
                .append(expirationDate)
                .append(SECURITY_KEY)
                .toString();

        return hexBinaryAdapter.marshal(messageDigest.digest(getBytesUtf8(token)));
    }
}
