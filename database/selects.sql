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