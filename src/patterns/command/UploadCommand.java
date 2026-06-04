package patterns.command;

public class UploadCommand
        implements Command {

    @Override
    public void execute() {

        System.out.println(
                "Archivo enviado");
    }
}