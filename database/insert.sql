INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (1, 'ivo@mail.com', '089671253', 'ivo12345678', 'Ivaylo', 'Rumenov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (2, 'deme@mail.com', '089345345', 'deme12345678', 'Damqn', 'Mihaylov');
/*NOT ADDED*/
INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (3, 'emo@mail.com', '089653453', 'emo12345678', 'E,amuil', 'Glavchev');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (4, 'asen@mail.com', '089673245', 'ase12345678', 'Asen', 'Hristov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (5, 'ivo@mail.com', '089623543', 'ivo12345678', 'Ivaylo', 'Rumenov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (6, '', '0854356', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (7, '', '0863456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (8, '', '0836456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (9, '', '083456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (10, '', '0863456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (11, '', '083456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (12, '', '0863456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (13, '', '0834566', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (14, '', '0854363456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (15, '', '08634563', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (16, '', '0834563456', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (17, '', '08364563', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (18, '', '08634563', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (19, '', '08345634', '', '', '');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (20, '', '0834563456', '', '', '');

/*
    Frientds creating a new chat
*/

INSERT INTO friend_relation(id_friend,id_user)
VALUES(1,2);

/* Create new conversation */

INSERT INTO conversation(conversation_name,id)
VALUES('Ivaylo_Damqn',1);

/* Added ivo to chat 1*/
INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(1,1);

/* Added ivo to chat 2*/
INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(1,2);

/* Add 5 test massages */
INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp)
VALUES('Test messages - 1- Reciver Damqn; Sender Ivo  Order:1',1,1,1,3,'2023-11-27');

INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp)
VALUES('Test messages - 2- Reciver Ivo; Sender Damqn  Order:2',1,2,2,3,'2023-11-27');

INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp)
VALUES('Test messages - 3- Reciver Damqn; Sender Ivo  Order:3',1,1,3,3,'2023-11-27');

INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp)
VALUES('Test messages - 4- Reciver Ivo; Sender Damqn  Order:4',1,2,4,3,'2023-11-27');

INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp)
VALUES('Test messages - 5- Reciver Damqn; Sender Ivo  Order:5',1,1,5,3,'2023-11-27');
