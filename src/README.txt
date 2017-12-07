README.txt

To run the client type: java Client web_address optional port.
For example:

java Client www.example.org 80

java Client www.example.org 

java Client www.cs.ksu.edu 12145

java Client www.cs.ksu.edu 

The code will out put the page if the status code in the response is 200 (OK). Otherwise it will return the error code 
line to give the user an idea of why the request failed. 

I am assuming the page the enter when asked "What page? ->" is a valid page to get. 

