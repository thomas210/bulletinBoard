# BulletinBoardFront

Technical challenge for the CERS company carried out by Thomás Tabosa de Oliveira. The system consists of a notice management.

To install the dependencies it is necessary to run the command

This project was carried out with maven's dependency manager, so it is necessary to install it to run.

The table used in the system was made with the SQL code below

```bash
CREATE TABLE `bulletin_board`.`notice` (
 `id` INT NOT NULL,
 `title` VARCHAR(255) NOT NULL,
 `description` VARCHAR(255) NULL,
 `publicationDate` TIMESTAMP NULL,
 `visualizationDate` TIMESTAMP NULL,
 PRIMARY KEY (`id`));

ALTER TABLE `bulletin_board`.`notice`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ,
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);
 
DROP TABLE `bulletin_board`.`notice`
```