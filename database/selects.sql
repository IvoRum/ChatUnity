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
select fr.id_friend, uu.first_name, uu.family_name
from friend_relation fr
         join public.unity_user uu on uu.id = fr.id_friend
where fr.id_user = 2
