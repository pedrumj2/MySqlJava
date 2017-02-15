# MySqlJava
A library that facilitates working with mysql through java.

#SqlConnect
A wrapper class for the jdbc connector. This class can be used to initate a connection to the database and get a statement object.

#dbParams
Used to pass database parameters into the SqlConnect class.

#Chunk
Provides an interface to the user for looping through each row of a table. Under the hood it makes a request and gets a chunk of rows 
and returns rows one at a time upon user request.
