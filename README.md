# MySqlJava
A library that facilitates working with mysql through java.

#SqlConnect
A wrapper class for the jdbc connector. This class can be used to initate a connection the database and get a statement object.

#dbParams
Used to pass database parameters into the SqlConnect class.

#Chunk
Give an interface to the user to loop through eachr row of a table one by one. Under the hood it makes a request and gets a chunk of rows 
and returns rows one at a time upon user request.
