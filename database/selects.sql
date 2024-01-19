/*
    All selects used in Unity chat Server respository
*/

/* Heat check repository*/

select Unity_user.id from Unity_user order by Unity_user.id desc limit 1;

/*
    FRIEND
*/

/* Recive friend Handles */

select fr.id_friend, uu.first_name, uu.family_name
from friend_relation fr
         join public.unity_user uu on uu.id = fr.id_friend
where fr.id_user = 2;

/* Select all frineds conversations */
select  uu.first_name, uu.family_name, c.id,count(m.message_order) as ms_order
from friend_relation fr
         join public.unity_user uu on uu.id = fr.id_friend
         join user_conversation_relation uc on uu.id = uc.id_user
         join conversation c on uc.id_conversation = c.id
         join public.message m on c.id = m.id_reciver
where fr.id_user = 2
group by fr.id_friend, uu.first_name, uu.family_name, c.idselect  uu.first_name, uu.family_name, c.id,count(m.message_order) as ms_order
from friend_relation fr
         join public.unity_user uu on uu.id = fr.id_friend
         join user_conversation_relation uc on uu.id = uc.id_user
         join conversation c on uc.id_conversation = c.id
         join public.message m on c.id = m.id_reciver
where fr.id_user = 2
group by fr.id_friend, uu.first_name, uu.family_name, c.id

/* Selecting all grpups of a user*/
SELECT c.conversation_name,
       array_agg(DISTINCT u.id) AS user_ids,
       COUNT(DISTINCT u.id) AS user_count
FROM public.conversation c
JOIN user_conversation_relation cu ON c.id = cu.id_conversation
JOIN unity_user u ON cu.id_user = u.id
GROUP BY c.conversation_name
HAVING COUNT(DISTINCT u.id) > 2;

SELECT c.conversation_name,c.id as conversation_id,
       array_agg(DISTINCT u.id) AS user_ids,
       COUNT(DISTINCT u.id) AS user_count
FROM public.conversation c
JOIN user_conversation_relation cu ON c.id = cu.id_conversation
JOIN unity_user u ON cu.id_user = u.id
GROUP BY c.conversation_name,c.id
HAVING COUNT(DISTINCT u.id) > 2 AND ARRAY[2]::int[] <@ array_agg(DISTINCT u.id)::int[];


/* Select unread messages*/
select uu.first_name from message ms
join conversation co on co.id=ms.id_reciver
join user_conversation_relation ucr on ucr.id_conversation = co.id
join unity_user uu on uu.id=ucr.id_user
where uu.id=2
and ms.message_status=2

/* Get last conversation id */

select cc.id from conversation cc
order by cc.id desc limit 1

/* Add friend */

INSERT INTO friend_relation(id_friend,id_user)
VALUES(8,11);

INSERT INTO friend_relation(id_friend,id_user)
VALUES(11,8);

INSERT INTO conversation(conversation_name,id)
VALUES('8_7',((select cc.id from conversation cc
order by cc.id desc limit 1)+1));

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES((select cc.id from conversation cc
order by cc.id desc limit 1),8);

INSERT INTO user_conversation_relation(id_conversation,id_user)
VALUES((select cc.id from conversation cc
order by cc.id desc limit 1),11);

INSERT INTO message(content,id_reciver,id_sender,message_order,message_status,time_stamp)
VALUES('Hello',(select cc.id from conversation cc
order by cc.id desc limit 1),8,1,2,CURRENT_TIMESTAMP);

/*Old selects*/

select uu.first_name, ms.id_sender, ms.message_order, ms.message_status, ms.content 
from message ms 
join public.unity_user uu on uu.id = ms.id_sender 
where id_reciver=1
and message_order > 3 and message_order < 103


update public.message set message_status=4 where id_reciver=1 and message_order > 3 and message_order < 103

select us.first_name,ms.content from message ms
join conversation co on co.id=ms.id_reciver
join user_conversation_relation ucr on ucr.id_conversation = co.id
join unity_user uu on uu.id=ucr.id_user
join unity_user us on us.id=ms.id_sender
where uu.id=2
and ms.message_status=2

