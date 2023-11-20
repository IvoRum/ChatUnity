CREATE TABLE Unity_user(
    id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    telephone VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    family_name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Friend_relation(
    id_user BIGINT NOT NULL,
    id_friend BIGINT NOT NULL
);

CREATE TABLE Message(
    id_sender BIGINT NOT NULL,
    id_reciver BIGINT NOT NULL,
    message_order BIGINT NOT NULL,
    time_stamp TIMESTAMPTZ NOT NULL,
    content VARCHAR(255) NOT NULL,
    message_status BIGINT NOT NULL CHECK (message_status > 0 AND message_status < 4),
    PRIMARY KEY(id_sender, id_reciver, message_order)
);

CREATE TABLE Conversation(
    id BIGINT NOT NULL,
    conversation_name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE User_conversation_relation(
    id_user BIGINT NOT NULL,
    id_conversation BIGINT NOT NULL
);

ALTER TABLE
   Friend_relation ADD CONSTRAINT fk_friend_relation_user FOREIGN KEY(id_user) REFERENCES Unity_user(id);

ALTER TABLE
   Friend_relation ADD CONSTRAINT fk_friend_relation_friend FOREIGN KEY(id_friend) REFERENCES Unity_user(id);

ALTER TABLE
   Message ADD CONSTRAINT fk_message_user FOREIGN KEY(id_sender) REFERENCES Unity_user(id);

ALTER TABLE
   User_conversation_relation ADD CONSTRAINT fk_user_conversation_relation_user FOREIGN KEY(id_user) REFERENCES Unity_user(id);

ALTER TABLE
   User_conversation_relation ADD CONSTRAINT fk_user_conversation_relation_conversation FOREIGN KEY(id_conversation) REFERENCES Conversation(id);

ALTER TABLE
   Message ADD CONSTRAINT fk_message_conversation FOREIGN KEY(id_reciver) REFERENCES Conversation(id);
