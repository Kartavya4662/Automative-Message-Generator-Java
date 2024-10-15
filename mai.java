import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TherapyBotServer {

	private static final String[] motivationalMessages = {
		    "You are stronger than you think.",
		    "Believe in yourself, and you can achieve anything.",
		    "The only way to do great work is to love what you do.",
		    "Success is not final, failure is not fatal: It is the courage to continue that counts.",
		    "Your time is limited, don't waste it living someone else's life.",
		    // Add more motivational messages here
		};

		private static final String[] jokes = {
		    "Why don't scientists trust atoms? Because they make up everything!",
		    "Parallel lines have so much in common. It's a shame they'll never meet.",
		    "Why do we never tell secrets on a farm? Because the potatoes have eyes, the corn has ears, and the beans stalk!",
		    "What do you call a bear with no teeth? A gummy bear!",
		    "I used to play piano by ear, but now I use my hands.",
		    // Add more jokes here
		};

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    OutputStream os = exchange.getResponseBody();

                    exchange.getResponseHeaders().set("Content-Type", "text/html");
                    exchange.sendResponseHeaders(200, 0);

                    String htmlResponse = "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "    <title>TherapyBot</title>\n" +
                            "    <style>\n" +
                            "        body {\n" +
                            "            font-family: Arial, sans-serif;\n" +
                            "            background-color: #f2f2f2;\n" +
                            "            text-align: center;\n" +
                            "            margin: 0;\n" +
                            "            padding: 0;\n" +
                            "        }\n" +
                            "        #header, #footer {\n" +
                            "            background-color: #009688;\n" +
                            "            color: #fff;\n" +
                            "            padding: 20px;\n" +
                            "        }\n" +
                            "        #content {\n" +
                            "            max-width: 800px;\n" +
                            "            margin: 0 auto;\n" +
                            "            padding: 20px;\n" +
                            "            background-color: #fff;\n" +
                            "            border-radius: 10px;\n" +
                            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);\n" +
                            "            text-align: left;\n" +
                            "        }\n" +
                            "        button, a {\n" +
                            "            background-color: #009688;\n" +
                            "            color: #fff;\n" +
                            "            border: none;\n" +
                            "            padding: 10px 20px;\n" +
                            "            border-radius: 5px;\n" +
                            "            cursor: pointer;\n" +
                            "            margin-top: 10px;\n" +
                            "            text-decoration: none;\n" +
                            "            display: inline-block;\n" +
                            "        }\n" +
                            "        button:hover, a:hover {\n" +
                            "            background-color: #007a6b;\n" +
                            "        }\n" +
                            "        .section {\n" +
                            "            background-color: #fff;\n" +
                            "            border-radius: 10px;\n" +
                            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);\n" +
                            "            text-align: left;\n" +
                            "            padding: 20px;\n" +
                            "            margin: 20px 0;\n" +
                            "        }\n" +
                            "        .section input[type='text'] {\n" +
                            "            width: 100%;\n" +
                            "            padding: 10px;\n" +
                            "            border: 1px solid #ccc;\n" +
                            "            border-radius: 5px;\n" +
                            "        }\n" +
                            "        .popup {\n" +
                            "            display: none;\n" +
                            "            position: fixed;\n" +
                            "            top: 50%;\n" +
                            "            left: 50%;\n" +
                            "            transform: translate(-50%, -50%);\n" +
                            "            background-color: #fff;\n" +
                            "            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);\n" +
                            "            padding: 20px;\n" +
                            "            border-radius: 5px;\n" +
                            "        }\n" +
                            "        .popup h3 {\n" +
                            "            margin-top: 0;\n" +
                            "        }\n" +
                            "        .popup-close {\n" +
                            "            background-color: #ff6347;\n" +
                            "            color: #fff;\n" +
                            "            border: none;\n" +
                            "            padding: 10px 20px;\n" +
                            "            border-radius: 5px;\n" +
                            "            cursor: pointer;\n" +
                            "            margin-top: 10px;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <div id=\"header\">\n" +
                            "        <h1>Welcome to TherapyBot</h1>\n" +
                            "        <p>Your Personal Therapy Assistant</p>\n" +
                            "    </div>\n" +
                            "    <div id=\"content\">\n" +
                            "        <h2>Stay Inspired with TherapyBot</h2>\n" +
                            "        <p>TherapyBot is here to provide you with continuous motivation and support to maintain your mental well-being.</p>\n" +
                            "        <button onclick=\"startTherapyBot()\">Start TherapyBot</button>\n" +
                            "        <button onclick=\"stopTherapyBot()\">Stop TherapyBot</button>\n" +
                            "    </div>\n" +
                            "    <div class=\"section\">\n" +
                            "        <h2>Motivational Quotes</h2>\n" +
                            "        <div id=\"motivational-quotes\"></div>\n" +
                            "        <button onclick=\"showRandomMotivation()\">Get Inspired</button>\n" +
                            "    </div>\n" +
                            "    <div class=\"section\">\n" +
                            "        <h2>Jokes for a Smile</h2>\n" +
                            "        <div id=\"jokes\"></div>\n" +
                            "        <button onclick=\"showRandomJoke()\">Tell Me a Joke</button>\n" +
                            "    </div>\n" +
                            "    <div class=\"section\">\n" +
                            "        <h2>Tell us about your day</h2>\n" +
                            "        <input type=\"text\" id=\"user-input\" placeholder=\"Share your thoughts...\">\n" +
                            "        <button onclick=\"submitUserInput()\">Submit</button>\n" +
                            "    </div>\n" +
                            "    <div class=\"section\">\n" +
                            "        <h2>Explore Around</h2>\n" +
                            "        <p>Explore our latest articles, tips, and expert advice on maintaining your mental health and well-being.</p>\n" +
                            "        <a href=\"#\" onclick=\"exploreAdditionalSection2()\">Explore</a>\n" +
                            "    </div>\n" +
                            "    <div id=\"footer\">\n" +
                            "        <p>&copy; 2023 TherapyBot. All rights reserved.</p>\n" +
                            "        <p><a href=\"#\">Privacy Policy</a> | <a href=\"#\">Terms of Service</a> | <a href=\"#\">Contact Us</a></p>\n" +
                            "    </div>\n" +
                            "    <div class=\"popup\" id=\"motivationPopup\">\n" +
                            "        <h3>Motivational Quote</h3>\n" +
                            "        <p id=\"motivationPopupContent\"></p>\n" +
                            "        <button class=\"popup-close\" onclick=\"closePopup('motivationPopup')\">Close</button>\n" +
                            "    </div>\n" +
                            "    <div class=\"popup\" id=\"jokePopup\">\n" +
                            "        <h3>Joke</h3>\n" +
                            "        <p id=\"jokePopupContent\"></p>\n" +
                            "        <button class=\"popup-close\" onclick=\"closePopup('jokePopup')\">Close</button>\n" +
                            "    </div>\n" +
                            "    <script>\n" +
                            "        var isTherapyBotRunning = false;\n" +
                            "        var motivationInterval, jokeInterval;\n" +
                            "        \n" +
                            "        function startTherapyBot() {\n" +
                            "            if (!isTherapyBotRunning) {\n" +
                            "                alert('TherapyBot started!');\n" +
                            "                isTherapyBotRunning = true;\n" +
                            "                motivationInterval = setInterval(showRandomMotivation, 10000);\n" +
                            "                jokeInterval = setInterval(showRandomJoke, 15000);\n" +
                            "            }\n" +
                            "        }\n" +
                            "        \n" +
                            "        function stopTherapyBot() {\n" +
                            "            if (isTherapyBotRunning) {\n" +
                            "                alert('TherapyBot stopped!');\n" +
                            "                isTherapyBotRunning = false;\n" +
                            "                clearInterval(motivationInterval);\n" +
                            "                clearInterval(jokeInterval);\n" +
                            "            }\n" +
                            "        }\n" +
                            "        \n" +
                            "        function showRandomMotivation() {\n" +
                            "            var messages = " + getRandomMotivationalMessages() + ";\n" +
                            "            var randomIndex = Math.floor(Math.random() * messages.length);\n" +
                            "            var motivationPopup = document.getElementById('motivationPopup');\n" +
                            "            var motivationContent = document.getElementById('motivationPopupContent');\n" +
                            "            motivationContent.innerHTML = messages[randomIndex];\n" +
                            "            motivationPopup.style.display = 'block';\n" +
                            "        }\n" +
                            "        \n" +
                            "        function showRandomJoke() {\n" +
                            "            var jokes = " + getRandomJokes() + ";\n" +
                            "            var randomIndex = Math.floor(Math.random() * jokes.length);\n" +
                            "            var jokePopup = document.getElementById('jokePopup');\n" +
                            "            var jokeContent = document.getElementById('jokePopupContent');\n" +
                            "            jokeContent.innerHTML = jokes[randomIndex];\n" +
                            "            jokePopup.style.display = 'block';\n" +
                            "        }\n" +
                            "        \n" +
                            "        function closePopup(popupId) {\n" +
                            "            var popup = document.getElementById(popupId);\n" +
                            "            popup.style.display = 'none';\n" +
                            "        }\n" +
                            "        \n" +
                            "        function submitUserInput() {\n" +
                            "            var userInput = document.getElementById('user-input').value;\n" +
                            "            alert('You submitted: ' + userInput);\n" +
                            "            // You can add code here to process the user's input.\n" +
                            "        }\n" +
                            "        \n" +
                            "        function exploreAdditionalSection2() {\n" +
                            "            // 2.\n" +
                            "        }\n" +
                            "    </script>\n" +
                            "</body>\n" +
                            "</html>";

                    os.write(htmlResponse.getBytes());
                    os.close();
                }
            });

            server.start();
            System.out.println("Server started at http://localhost:8080/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRandomMotivationalMessages() {
        StringBuilder messagesArray = new StringBuilder("[");
        for (int i = 0; i < motivationalMessages.length; i++) {
            messagesArray.append("\"").append(motivationalMessages[i]).append("\"");
            if (i < motivationalMessages.length - 1) {
                messagesArray.append(", ");
            }
        }
        messagesArray.append("]");
        return messagesArray.toString();
    }

    private static String getRandomJokes() {
        StringBuilder jokesArray = new StringBuilder("[");
        for (int i = 0; i < jokes.length; i++) {
            jokesArray.append("\"").append(jokes[i]).append("\"");
            if (i < jokes.length - 1) {
                jokesArray.append(", ");
            }
        }
        jokesArray.append("]");
        return jokesArray.toString();
    }
}
