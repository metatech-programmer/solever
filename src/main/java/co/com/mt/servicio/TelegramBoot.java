/* package co.com.mt.servicio;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBoot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Aquí puedes manejar las actualizaciones de Telegram
        // por ejemplo, responder a mensajes recibidos
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hola, has enviado el siguiente mensaje: " + messageText);

        try {
            execute(message);  
        } catch (TelegramApiException e) {

        }
    }

    @Override
    public String getBotUsername() {
        // Devuelve el nombre de usuario de tu bot
        return "YourBotUsername";
    }

    @Override
    public String getBotToken() {
        // Devuelve el token de acceso de tu bot
        return "YourBotToken";
    }

}
 */