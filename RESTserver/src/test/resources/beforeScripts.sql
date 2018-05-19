DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `is_enabled` tinyint(1) NOT NULL,
  `role` enum('ROLE_REGULAR','ROLE_PREMIUM') NOT NULL,
  `premium_expires_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`role`),
  UNIQUE KEY `login_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Igor  Parada','igorParada@gmail.com','B59C67BF196A4758191E42F76670CEBA',1,'ROLE_PREMIUM',1491046542),(2,'Roman Dyndyn','romanDyndyn@gmail.com','934B535800B1CBA8F96A5D72F72F1611',1,'ROLE_PREMIUM',1490980597),(3,'Pavlo Bendus','pavloBendus@gmail.com','2BE9BD7A3434F7038CA27D1918DE58BD',1,'ROLE_REGULAR',1490989451),(4,'Igor Kryviuk','igorKryviuk@gmail.com','DBC4D84BFCFE2284BA11BEFFB853A8C4',1,'ROLE_REGULAR',1491039623),(5,'Oleh Pochernin','olehPochernin@gmail.com','6074C6AA3488F3C2DDDFF2A7CA821AAB',1,'ROLE_PREMIUM',0),(6,'Vitaliy Malisevych','vitaliyMalisevych@gmail.com','E9510081AC30FFA83F10B68CDE1CAC07',1,'ROLE_REGULAR',1490740922),(7,'Nazar Vynnyk','nazarVynnyk@gmail.com','D79C8788088C2193F0244D8F1F36D2DB',1,'ROLE_PREMIUM',1491346671),(8,'Java Bean','javaBean@oracle.com','20D50A5D6A5D4105C84C3069AAFE8998',1,'ROLE_PREMIUM',0),(9,'Billy','billGates@microsoft.com','B59C67BF196A4758191E42F76670CEBA',1,'ROLE_REGULAR',0),(10,'&lt;b&gt;123&lt;b&gt;','abc@ukr.net','81DC9BDB52D04DC20036DBD8313ED055',1,'ROLE_REGULAR',0),(11,'Василий','vasiaPupkin@gmail.com','B59C67BF196A4758191E42F76670CEBA',1,'ROLE_REGULAR',0),(12,'<b>Masha</b>','masha11@mail.ru','DB4EFEF01A1C290E3C4F7E841AF7875C',1,'ROLE_REGULAR',0),(13,'&lt;b&gt;Petya&lt;/b&gt;','petya11@mail.ru','A653DF7D8A57FD08C211E13E9970D5BA',1,'ROLE_PREMIUM',1491035596),(14,'Tester','test@gmail.com','81DC9BDB52D04DC20036DBD8313ED055',1,'ROLE_REGULAR',0),(15,'Mdam','mda@mda.com','B0BAEE9D279D34FA1DFD71AADB908C3F',1,'ROLE_REGULAR',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;