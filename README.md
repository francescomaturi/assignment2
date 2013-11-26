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

**GET  /person**

Returns the list of all the people in the database 

**POST  /person**

Creates a new person in our database and returns it with the generated identifier that can be used after to access to that person. The person object you want to create should be passed as body of the request. This method support both json and xml request format. Just set the header `Content-Type: application/json` or `Content-Type: application/xml` . For example the body of a json request could be: `{"firstname":"Francesco","lastname":"Maturi","birthdate":"27-01-1990","height":"1.89","weight":"89.2"}`.

**GET  /person/{p_id}**

Returns the person associated to that specific **p_id**. The person object is returned with the current healthprofile and also le list of healthprofile_ids of that person. If there isn't a person associated with the given **p_id** the response status will be 204 NO_CONTENT.

**PUT  /person/{p_id}**

This method is for updating the person information like firstname, lastname, birthdate or the current weight and height. The updated person object should be passed as body of the request. This method support both json and xml request format. Just set the header `Content-Type: application/json` or `Content-Type: application/xml`. For example the body of a json request could be: `{"firstname":"Francesco","lastname":"Maturi","birthdate":"27-01-1990","height":"1.89","weight":"89.2"}`. If weight and height are also updated will be saved in the healthprofile history the old healthprofile. The person updated will be returned in the body of the response.

**DELETE  /person/{p_id}**

Delete the person identified by that specific **p_id** and also his healthprofile history. This method returns to the caller all the information that are deleted.

**GET  /person/{p_id}/healthprofile**

Returns the specified person with all the data relating to its healthprofile history.

**POST  /person/{p_id}/healthprofile**

Updates the new healthprofile of the specified person. The current healthprofile of this person will be put in his healthprofile history and replaced by the updated data that are given. This method support both json and xml request format. Just set in the headers what you prefer `Content-Type: application/json` or `Content-Type: application/xml`. For example the body of an xml request could be: `<healthProfile><height>1.74</height><weight>70.3</weight></healthProfile>`. The response will be the updated person with all the data relating to its healthprofile history.

**GET  /person/p_id/healthprofile/hp_id**

Returns the specified healthprofile of the specified person. If there isn't an healthprofile identified by **hp_id** associated to the given **p_id** the response will be 204 NO_CONTENT.

**PUT  /person/p_id/healthprofile/hp_id**

Updates weight and height of the specified healthprofile of the specified person. This method support both json and xml request format. Just set in the headers what you prefer `Content-Type: application/json` or `Content-Type: application/xml`. For example the body of an xml request could be: `<healthProfile><height>1.80</height><weight>75.3</weight></healthProfile>`. The response will contains be the updated healthprofile.

**DELETE  /person/p_id/healthprofile/hp_id**

Delete the specified healthprofile. The data removed from the database will be returned in the response.

**GET  /search/birthdate?from=DD-MM-YYYY&to=DD-MM-YYYY**

Returns all the people that have the birthday in the specified range.

**GET  /search/profile?measure={height|weight}&min=MIN&max=MAX**

Returns all the people that are in the specified range of height or weight.

**GET  /search/name?q=TEXT_TO_SEARCH**

Returns the people that have firstname or lastname matching the TEXT_TO_SEARCH 
