package com.example.xmlexercise;

import com.example.xmlexercise.models.dtos.UserAddDto;
import com.example.xmlexercise.models.dtos.UsersDto;
import com.example.xmlexercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Controller
public class ApplicationConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private JAXBContext jaxbContext;

    @Autowired
    public ApplicationConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        //addUserFromXML();

        //exportUserToXML();

        //addMultipleUsersFromXML();

        //exportMultipleUsersToXML();
    }

    private void addUserFromXML() throws JAXBException, FileNotFoundException {
        this.jaxbContext = JAXBContext.newInstance(UserAddDto.class);
        BufferedReader bfr = new BufferedReader(new FileReader("src/main/resources/files/user.xml"));
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        UserAddDto userAddDto = (UserAddDto) unmarshaller.unmarshal(bfr);

        this.userService.addUser(userAddDto);
    }

    private void exportUserToXML() throws JAXBException {
        UserAddDto randomUser = this.userService.getRandomUser();
        this.jaxbContext = JAXBContext.newInstance(UserAddDto.class);
        Marshaller marshaller = this.jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(randomUser, new File("src/main/resources/files/outputs/userExport.xml"));
    }

    private void addMultipleUsersFromXML() throws JAXBException, FileNotFoundException {
        this.jaxbContext = JAXBContext.newInstance(UsersDto.class);
        BufferedReader bfr = new BufferedReader(new FileReader("src/main/resources/files/users.xml"));
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        UsersDto usersDto = (UsersDto) unmarshaller.unmarshal(bfr);
        List<UserAddDto> users = usersDto.getUsers();
        users.forEach(this.userService::addUser);
    }

    private void exportMultipleUsersToXML() throws JAXBException {
        UsersDto usersDto = new UsersDto(this.userService.getAllUsers());
        this.jaxbContext = JAXBContext.newInstance(UsersDto.class);
        Marshaller marshaller = this.jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usersDto, new File("src/main/resources/files/outputs/usersExport.xml"));
    }
}
