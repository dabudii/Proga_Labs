package server.utility;

import general.interaction.Profile;
import general.interaction.Request;
import general.interaction.Response;
import general.interaction.ResponseCode;

import java.util.concurrent.RecursiveTask;

/**
 * Handles requests.
 */
public class RequestHandler extends RecursiveTask<Response> {
    private Request request;
    private CommandManager commandManager;

    public RequestHandler(Request request,CommandManager commandManager) {
        this.request = request;
        this.commandManager = commandManager;
    }

    public Response compute() {
        Profile profile = new Profile(request.getProfile().getUsername(),request.getProfile().getPassword());
        commandManager.addToHistory(request.getCommandName(), request.getProfile());
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument(), profile);
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private ResponseCode executeCommand(String command, String commandStringArgument,
                                        Object commandObjectArgument, Profile profile) {
        switch (command) {
            case "":
                break;
            case "help":
                if(!commandManager.help(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "info":
                if(!commandManager.info(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.update(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "add_if_min":
                if (!commandManager.addIfMin(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "remove_lower":
                if (!commandManager.removeLower(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "history":
                if (!commandManager.history(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "remove_all_by_difficulty":
                if (!commandManager.removeAllByDifficulty(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "server_save":
                if(!commandManager.serverSave(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "filter_starts_with_name":
                if (!commandManager.filterStartsWithName(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "print_descending":
                if (!commandManager.printDescending(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "login":
                if (!commandManager.login(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            case "register":
                if (!commandManager.register(commandStringArgument, commandObjectArgument, profile))
                    return ResponseCode.ERROR;
                break;
            default:
                ResponseOutputer.appendln("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}