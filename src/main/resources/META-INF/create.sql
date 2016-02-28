CREATE TABLE `user` ( `id` int(10) unsigned NOT NULL AUTO_INCREMENT, `login` varchar(30) NOT NULL, `password_hash` varbinary(40) NOT NULL, `password_salt` varbinary(16) NOT NULL, `full_name` varchar(150) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `login` (`login`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;