INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (1, 'ivo@mail.com', '089671253', 'ivo12345678', 'Ivaylo', 'Rumenov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (2, 'deme@mail.com', '089345345', 'deme12345678', 'Damqn', 'Mihaylov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (3, 'emo@mail.com', '089653453', 'emo12345678', 'E,amuil', 'Glavchev');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (4, 'asen@mail.com', '089673245', 'ase12345678', 'Asen', 'Hristov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (5, 'ivo@mail.com', '089623543', 'ivo12345678', 'Ivaylo', 'Rumenov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (6, 'iliq@gmail.com', '0854356', 'iliq12345678', 'Iliq', 'Ivanov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (7, 'hasan@outlook.com', '0863456', 'hasan1233455467','Hasan', 'Mohamedov');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (8, 'David@gmail.com', '0836456', 'david12345678', 'David', 'Gogings');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (9, 'JoeRogan@outlook.com', '083456', 'joe12345678', 'Joe', 'Rogan');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (10, 'DavidChoe@gmail.com', '0863456', 'david12345678', 'David', 'Choe');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (11, 'mariq@mail.com', '083456', 'mariq12345678', 'Mariq', 'Ilieva');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (12, 'JoeyDiaz@gmail.com', '0863456', 'joey12345678', 'Joey', 'Diaz');

INSERT INTO Unity_user(id, email, telephone, password, first_name, family_name)
VALUES (13, 'Trauma@outlook.com', '0834566', 'trauma12345678', 'Trauma', 'Kokov');

/*
    Frientds creating a new chat
*/
INSERT INTO friend_relation(id_friend,id_user)
VALUES(1,2);

INSERT INTO friend_relation(id_friend,id_user)
VALUES(3,2);

INSERT INTO friend_relation(id_friend,id_user)
VALUES(4,2);

INSERT INTO friend_relation(id_friend,id_user)
VALUES(7,2);

INSERT INTO friend_relation(id_friend,id_user)
VALUES(12,2);

/* Create new conversation */
INSERT INTO conversation(conversation_name,id)
VALUES('Ivaylo_Damqn',1);

INSERT INTO conversation(conversation_name,id)
VALUES('Damqn_Emo',2);

INSERT INTO conversation(conversation_name,id)
VALUES('Damqn_Asen',3);

INSERT INTO conversation(conversation_name,id)
VALUES('Damqn_Hasan',4);

INSERT INTO conversation(conversation_name,id)
VALUES('Damqn_Joe',5);

/* Added ivo to chat 1*/
INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(1,1);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(1,2);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(2,2);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(2,3);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(3,2);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(3,4);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(4,2);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(4,7);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(5,2);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES(5,12);

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
