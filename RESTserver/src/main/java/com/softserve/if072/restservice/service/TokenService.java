package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.User;
import com.softserve.if072.restservice.dao.mybatisdao.UserDAO;
import com.softserve.if072.restservice.security.authentication.CustomAuthenticationToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * TokenService is utility class which contains methods to operate with {@link CustomAuthenticationToken} class
 *
 * @author Igor Parada
 */
@Service
public class TokenService {

    private static final Logger LOGGER = LogManager.getLogger(TokenService.class);

    private final String SECURITY_KEY;
    private final String TOKEN_DELIMITER;
    private final int TOKEN_VALIDITY_TIME;

    private Environment environment;
    private UserDAO userDAO;
    private MessageDigest messageDigest;
    private HexBinaryAdapter hexBinaryAdapter;

    @Autowired
    public TokenService(Environment environment, UserDAO userDAO, MessageDigest messageDigest, HexBinaryAdapter adapter) {
        this.environment = environment;
        this.userDAO = userDAO;
        this.messageDigest = messageDigest;
        this.hexBinaryAdapter = adapter;
        this.SECURITY_KEY = environment.getProperty("security.tokenEncryptionKey");
        this.TOKEN_DELIMITER = environment.getProperty("security.tokenDelimiter");
        this.TOKEN_VALIDITY_TIME = Integer.parseInt(environment.getProperty("security.tokenValidityTimeInSeconds"));
    }

    /**
     * Perform decoding and validating of input token and if it's valid, fills its fields with parsed values.
     * If validation were successful, sets {@code isValid} value of token to true.
     *
     * @param token an {@link CustomAuthenticationToken} instance
     */
    public void validate(CustomAuthenticationToken token) {
        String[] tokenParts = decodeToken(token.getToken());
        if (tokenParts != null) {
            token.setUserName(tokenParts[0]);

            try {
                token.setExpirationDate(Long.parseLong(tokenParts[1]));
            } catch (NumberFormatException e) {
                LOGGER.warn("Can't parse token expiration data. Date format is incorrect");
                return;
            }

            if (new DateTime(token.getExpirationDate()).isBeforeNow()) {
                LOGGER.info("Token's validity time expired, new token must be generated");
                return;
            }

            token.setConfirmationKey(tokenParts[2]);
            String expectedKey = buildTokenConfirmationKey(token.getUserName(), token.getExpirationDate());
            if (!expectedKey.equals(token.getConfirmationKey())) {
                LOGGER.warn("Received key does not match the expected one: " + token.getConfirmationKey() + " <> " + expectedKey);
                return;
            }

            //if there is no errors, set token as valid
            token.setValid(true);
        }
    }

    /**
     * Retrieves {@link User} instance from DB by given token
     *
     * @param token valid {@link CustomAuthenticationToken} instance
     * @return instance of {@link User} class retrieved from DB or null if such user not found
     */
    public User getUserByToken(CustomAuthenticationToken token) {
        return token.isValid() ? userDAO.getByUsername(token.getUserName()) : null;
    }

    /**
     * Generates token string for given username
     *
     * @return string with token value
     */
    public String generateTokenFor(String username) {
        long expirationDate = DateTime.now().plusSeconds(TOKEN_VALIDITY_TIME).getMillis();
        String tokenString = buildToken(username, expirationDate);
        return new String(Base64.encodeBase64(tokenString.getBytes()));
    }

    /**
     * Generates new token instead of the outdated one.
     *
     * @param authenticationToken token object to be renewed
     * @return String that contains renewed token
     */
    public String renewToken(CustomAuthenticationToken authenticationToken) {
        return generateTokenFor(authenticationToken.getUserName());
    }

    /**
     * Decodes input token string and splits result into array contains token's parts
     *
     * @param tokenString an Base64 string which contains token
     * @return array with decoded token parts or null if decoding fails
     */
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

    /**
     * Generates token string based on given username and expiration date and signs it with confirmation key
     *
     * @param username       given user's name
     * @param expirationDate token expiration date
     * @return token string
     * @see TokenService#buildTokenConfirmationKey
     */
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

    /**
     * Generates encrypted confirmation key for token based on user's name and expiration date.
     *
     * @return built token confirmation key as UTF-8 string
     */
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
