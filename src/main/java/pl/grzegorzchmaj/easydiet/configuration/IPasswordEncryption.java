package pl.grzegorzchmaj.easydiet.configuration;

public interface IPasswordEncryption {

    String generate(String input);
}
