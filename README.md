
# SMS Gateway

This application (SMS Gateway) connects to the WebSocket server. When it receives a JSON message, it extracts the receiver and message and sends out the corresponding SMS using an SMS gateway service.

# JSON Message Format: 
The server expects to receive messages in a structured JSON format:

receiver: The phone number to send the SMS to (e.g., "09xxxxxxxxx").

message: The text content of the SMS.

# Implementation Options

PieSocket: This is a cloud-based WebSocket provider. It handles a lot of the server setup and scaling for you, which is excellent if you want a quick and easy solution.

Local Server: This gives you more control and customization. You'll need to set up the WebSocket server software (e.g., using Node.js, Python, etc.) on your own server or computer.


## Detailed Explanation and Code Example (Node.js on a Local Server)

1. Install Dependencies:

```bash
  npm install ws
```
```js
const WebSocket = require('ws');
const port = 8080; // Choose your port

const wss = new WebSocket.Server({ port });

wss.on('connection', (ws) => {
    console.log('Client connected');

    ws.on('message', (message) => {
        try {
            const data = JSON.parse(message);
            const { receiver, messageText } = data;
            console.log(`Received SMS request: ${receiver}, ${messageText}`);

            // Use your SMS gateway API here to send the SMS
            sendSMS(receiver, messageText);
        } catch (error) {
            console.error('Error parsing JSON:', error);
        }
    });

    ws.on('close', () => {
        console.log('Client disconnected');
    });
});

function sendSMS(receiver, messageText) {
    // Implement your SMS sending logic using your preferred SMS gateway API
    // Example (Twilio):
    // const accountSid = 'YOUR_TWILIO_ACCOUNT_SID';
    // const authToken = 'YOUR_TWILIO_AUTH_TOKEN';
    // const client = require('twilio')(accountSid, authToken);
    // 
    // client.messages
    //       .create({body: messageText, from: 'YOUR_TWILIO_PHONE_NUMBER', to: receiver})
    //       .then(message => console.log(message.sid));
}

console.log(`WebSocket server running on port ${port}`);
```

    
