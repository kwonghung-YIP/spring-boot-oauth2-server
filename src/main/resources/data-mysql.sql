/*
  client_id               | varchar(256)  | NO   | PRI | NULL    |       |
| resource_ids            | varchar(256)  | YES  |     | NULL    |       |
| client_secret           | varchar(256)  | YES  |     | NULL    |       |
| scope                   | varchar(256)  | YES  |     | NULL    |       |
| authorized_grant_types  | varchar(256)  | YES  |     | NULL    |       |
| web_server_redirect_uri | varchar(256)  | YES  |     | NULL    |       |
| authorities             | varchar(256)  | YES  |     | NULL    |       |
| access_token_validity   | int(11)       | YES  |     | NULL    |       |
| refresh_token_validity  | int(11)       | YES  |     | NULL    |       |
| additional_information  | varchar(4096) | YES  |     | NULL    |       |
| autoapprove          

access_token_validity  (in second)
refresh_token_validity (in second)
additional_information:'{ "autoApprove": true, "autoApproveScopes": ["read"] }'

*/

delete from oauth_client_details;

insert into oauth_client_details (
  client_id, client_secret, 
  authorized_grant_types, 
  scope,
  web_server_redirect_uri,
  autoapprove
) values (
  'client-auth-code','{noop}password',
  'authorization_code,refresh_token',
  'read,write',
  'http://localhost:8080/login',
  ''
);

insert into oauth_client_details (
  client_id, client_secret, 
  authorized_grant_types, 
  scope,
  web_server_redirect_uri,
  autoapprove
) values (
  'client-password','{noop}password',
  'password,refresh_token',
  'read',
  'http://localhost:8080/login',
  'read'
);

insert into oauth_client_details (
  client_id, client_secret, 
  authorized_grant_types, 
  scope,
  web_server_redirect_uri,
  autoapprove
) values (
  'client-implicit','{noop}password',
  'implicit,refresh_token',
  'read',
  'http://localhost:8080/login',
  'read'
);

insert into oauth_client_details (
  client_id, client_secret, 
  authorized_grant_types, 
  scope,
  web_server_redirect_uri,
  autoapprove
) values (
  'client-client-cred','{noop}password',
  'client_credentials,refresh_token',
  'read',
  'http://localhost:8080/login',
  'read'
);