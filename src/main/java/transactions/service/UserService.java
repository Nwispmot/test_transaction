package transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import transactions.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation=Propagation.REQUIRED)
    public void insert(List<User> users){

        for (User user : users){
            System.out.println("Inserting Data for User name: " + user.getName());
            String sql = "INSERT into USER(Name, Dept, Salary) values (?, ?, ?)";
            jdbcTemplate.update(sql ,
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, user.getName());
                            preparedStatement.setString(2, user.getDept());
                            preparedStatement.setLong(3, user.getSalary());
                        }
                    });
        }
    }

    public List<User> getUsers() {
        String sql = "SELECT Name, Dept, Salary from User";
        System.out.println("Users in DB:");
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {

                return new User(resultSet.getString("Name"),
                        resultSet.getString("Dept"),
                        resultSet.getLong("Salary"));

            }
        });
        return userList;
    }


}
