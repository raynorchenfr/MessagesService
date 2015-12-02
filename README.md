A simple RESTful message web service which allows posting and looking up of short messages.
POST to /MessagesService/chat with a form containing fields named "username","text" and optionally, an integer "timeout" to post a short message that will time out after some seconds. Service will return a JSON object with the message ID
GET from /MessagesService/chat/{id} to view message by ID. Return type is a JSON object;
GET from /MessagesService/chats/{username} to view messages posted with a certain user name. Return type is a JSON array of the messages as JSON objects.
