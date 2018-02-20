/**
 * @file WebSocket client example
 * @author Timur Tibeyev
 * @version 1.0
 */

/** Opens new WebSocket connection **/
var socket = new WebSocket("ws://localhost:8080/websockets-1.0/ws");
socket.onmessage = onMessage;
socket.onerror = OnSocketError;

/**
 * Triggers when error occurs inside WebSocket connection.
 *
 * @param {Object} error - error object.
 */
function OnSocketError(error)
{
    console.log(error);
    alert('Error occurred');
}

/**
 * Triggers when new message received from the server.
 *
 * @param {Object} event - message object.
 */
function onMessage(event) {
    var res = JSON.parse(event.data);
    console.log(res);
    alert(res.status.code +" : "+res.status.message);
    if (res.action == "add" ||
        res.action == "subtract" ||
        res.action == "multiply" ||
        res.action == "divide") {
        if (res.status.code == 100) {
            document.getElementById('result').value = res.result;
        }
    } else {
        if (res.action == "messageFromServer") {
            alert("This message was triggered from server");
        }
    }
}

/**
 * Addition function.
 */
function add() {
    var var1 = document.getElementById('var1').value;
    var var2 = document.getElementById('var2').value;
    var action = "add";
    write(action, var1, var2);
}

/**
 * Subtraction function.
 */
function subtract() {
    var var1 = document.getElementById('var1').value;
    var var2 = document.getElementById('var2').value;
    var action = "subtract";
    write(action, var1, var2);
}

/**
 * Multiplication function.
 */
function multiply() {
    var var1 = document.getElementById('var1').value;
    var var2 = document.getElementById('var2').value;
    var action = "multiply";
    write(action, var1, var2);
}

/**
 * Division function.
 */
function divide() {
    var var1 = document.getElementById('var1').value;
    var var2 = document.getElementById('var2').value;
    var action = "divide";
    write(action, var1, var2);
}

/**
 * Sample function to demonstrate error response from server.
 */
function unsupported() {
    var var1 = document.getElementById('var1').value;
    var var2 = document.getElementById('var2').value;
    var action = "unsupported";
    write(action, var1, var2);
}

/**
 * Sample function to demonstrate error response from server.
 *
 * @param {String} action - calculation operator (sum, subtract, multiply, divide).
 * @param {String} var1 - first operand
 * @param {String} var2 - second operand
 */
function write(action, var1, var2) {
    var DeviceAction = {
        action: action,
        var1 : var1,
        var2 : var2
    };

    socket.send(JSON.stringify(DeviceAction));
}




