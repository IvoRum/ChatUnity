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
select  DISTINCT  uu.first_name, uu.family_name, ucr.id_conversation as ucr_of_friend, ucr.id_user, cc.id_conversation as ucr_of_user
from friend_relation fr
         join public.unity_user uu on uu.id = fr.id_friend
         join public.user_conversation_relation ucr on fr.id_friend= ucr.id_user
         join public.conversation c on c.id = ucr.id_conversation
         join public.user_conversation_relation cc on cc.id_user=fr.id_user
where fr.id_user = 2 and cc.id_conversation = ucr.id_conversation