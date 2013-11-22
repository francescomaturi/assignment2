DOCUMENTATION Assignment2
===========

Chrome Settings
----------------

To make a request outside our domain we have to set a flag for google chrome that allows make requests for any domain.

**MAC users:**
write on terminal `alias chrome="open /Applications/Google\ Chrome.app/ --args --disable-web-security";chrome`

**OTHER users:**
write on terminal `alias chrome="open <Your_Google_Chrome_path> --args --disable-web-security;chrome"`

With that command we create an alias to chrome to open it without web security flag so we can make requests to any
from our local files.

REST Service:
----------------
All our services support both json and xml format for response. 
To specify the response format just set the header `Accept: application/json` or `Accept: application/xml` for the type you require.

Here is the list of our rest services:

**GET /person **

Returns the list of the people in the database 

**POST /person**

This method support both json and xml request format. Just set the header `Content-Type: application/json` or `Content-Type: application/xml`


GET /person/{p_id}
