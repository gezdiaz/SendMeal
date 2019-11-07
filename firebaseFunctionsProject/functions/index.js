const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
 exports.helloWorld = functions.https.onRequest((request, response) => {
    console.log("La consola funciona");
    response.send("Hello from Firebase!");
 });

 exports.sendNotification = functions.https.onRequest((request, response) => {
    console.log("Entra a la funcion")
    const message = {
        notification: {
            title: 'You have been invited to a trip.',
            body: 'Tap here to check it out!'
        },
        topic: "pedidos"
    };
    
    console.log("Envía el mensaje");
    response.send(message);
    return admin.messaging().send(message)
            .then(function(response){
                console.log('Notification sent successfully:',response);
                return 0;
            }) 
            .catch(function(error){
                console.log('Notification sent failed:',error);
            });
 });
