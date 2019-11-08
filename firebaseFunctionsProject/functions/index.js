const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions

 exports.sendNotification = functions.https.onCall((data, context) => {
    console.log("Entra a la funcion")
    const message = {
        topic: "pedidos",
        data:{
            idPedido: data.idPedido,
            estadoAnterior: data.estadoAnterior,
            nuevoEstado: data.nuevoEstado
        }
    };
    
    console.log("Envía el mensaje");
    return admin.messaging().send(message)
            .then(function(response){
                console.log('Notification sent successfully:',response);
                return 0;
            }) 
            .catch(function(error){
                console.log('Notification sent failed:',error);
            });
 });
