Учебный проект "Написание простых приложений на Java"<br>
Содержит три модуля (file_splitter, regex_finder, delimiters_setter), компилируемых в три .jar файла соответственно.

## Usage example
```sh
java -jar file_splitter.jar input_file_name output_file_name_template<br>
java -jar regex_finder.jar regilar_expression input_file_name output_file_name<br>
java -jar delimiters_setter.jar delimiter input_file_name output_file_name
```
regex_finder.jar и delimiters_setter.jar могут принимать на вход более трех аргументов - первым, в таком случае будет regilar_expression или delimiter соответственно, последним - output_file_name. Все остальные аргументы - входные файлы.