DOCUMENTATION Assignment2
===========

Chrome Settings
----------------

To make a request outside our domain we have to set a flag for google chrome that allows make requests for any domain.

**MAC users:**
write on terminal `alias chrome="open /Applications/Google\ Chrome.app/ --args --disable-web-security"`

**OTHER users:**
write on terminal `alias chrome="open <Your_Google_Chrome_path> --args --disable-web-security"`

With that command we create an alias to chrome to open it without web security flag so we can make requests to any
from our local files.

REST Service:
----------------
All our services support both json and xml format for response. 
To specify the response format just set the header `Accept: application/json` or `Accept: application/xml` for the type you require.

Here is the list of our rest services:

**GET /person **

Returns the list of all the people in the database 

**POST /person**

Creates a new person in our database and returns it with our generated identifier that can be used after to access to that person. The person object you want to create should be passed as body of the request. This method support both json and xml request format. Just set the header `Content-Type: application/json` or `Content-Type: application/xml` . For example the body of a json request could be: `{"firstname":"Eric","lastname":"Leckner","birthdate":"27-05-1966"}`.

**GET /person/{p_id}**

Returns the person associated to that specific **p_id**. The person object returned has also the current healthprofile of the person if it's available. If we don't have a person associated with the given id the response status will be 204 NO_CONTENT.

**PUT /person/{p_id}**

This method is for updating the person information like firstname, lastname or birthdate. The updated person object should be passed as body of the request. This method support both json and xml request format. Just set the header `Content-Type: application/json` or `Content-Type: application/xml`.

**GET /person/{p_id}/healthprofile**

Returns the specified person with the history of his healthprofile.

-> to finish beacause you can specify before and after

**POST /person/{p_id}/healthprofile?height=YOUR_HEIGHT&weight=YOUR_WEIGHT**

Creates a new healthprofile for the given person with the passed parameters. Returns the person with his new current healthprofile.

**PUT /person/{p_id}/healthprofile?height=YOUR_HEIGHT&weight=YOUR_WEIGHT**

Updates the current healthprofile for the given person without creating a new one with the given parameters. If a person doesn't have a healthprofile this method does the same thing as the POST does, creates a new one. Returns the person with his updated current healthprofile.

**GET /person/{p_id}/healthprofile/{hp_id}** 

Returns the specified person with the specified healthprofile. If we don't have that specific healthprofile for that person the response status will be 204 NO_CONTENT.

