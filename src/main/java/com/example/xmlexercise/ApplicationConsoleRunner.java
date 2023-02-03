package com.example.xmlexercise;

import com.example.xmlexercise.models.dtos.UserAddDto;
import com.example.xmlexercise.models.dtos.UsersDto;
import com.example.xmlexercise.services.UserService;
import com.example.xmlexercise.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
public class ApplicationConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final XmlParser xmlParser;

    @Autowired
    public ApplicationConsoleRunner(UserService userService, XmlParser xmlParser) {
        this.userService = userService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
        addUserFromXML();

        exportUserToXML();

        addMultipleUsersFromXML();

        exportMultipleUsersToXML();

    }

    private void addUserFromXML() throws JAXBException, FileNotFoundException {
        UserAddDto userAddDto = (UserAddDto) this.xmlParser.unmarshalFile(UserAddDto.class, "src/main/resources/files/user.xml");
        this.userService.addUser(userAddDto);
    }

    private void exportUserToXML() throws JAXBException {
        UserAddDto randomUser = this.userService.getRandomUser();
        this.xmlParser.marshalFile(randomUser, "src/main/resources/files/outputs/userExport.xml");
    }

    private void addMultipleUsersFromXML() throws JAXBException, FileNotFoundException {
        UsersDto usersDto = (UsersDto) this.xmlParser.unmarshalFile(UsersDto.class, "src/main/resources/files/users.xml");
        List<UserAddDto> users = usersDto.getUsers();
        users.forEach(this.userService::addUser);
    }

    private void exportMultipleUsersToXML() throws JAXBException {
        UsersDto usersDto = new UsersDto(this.userService.getAllUsers());
        this.xmlParser.marshalFile(usersDto, "src/main/resources/files/outputs/usersExport.xml");
    }
}
