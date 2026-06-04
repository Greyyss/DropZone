package patterns.command;

public class LoginCommand
        implements Command {

    @Override
    public void execute() {

        System.out.println(
                "Usuario autenticado");
    }
}