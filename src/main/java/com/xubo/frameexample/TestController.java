package com.xubo.frameexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource(name = "dataSource")
    DataSource dataSource;

    @GetMapping(value = "/book/type")
    public List<BookType> getBookType() throws Exception {

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tbl_booktype");

        ArrayList<BookType> bookTypes = new ArrayList<>();

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            BookType bookType = new BookType();
            bookType.setId(resultSet.getInt(1));
            bookType.setTypeName(resultSet.getString(2));
            bookType.setTypeDesc(resultSet.getString(3));
            bookTypes.add(bookType);
        }
        return bookTypes;
    }

}
