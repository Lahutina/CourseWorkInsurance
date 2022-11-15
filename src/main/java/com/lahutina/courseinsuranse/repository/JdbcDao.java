package com.lahutina.courseinsuranse.repository;

import com.lahutina.courseinsuranse.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcDao {

    private static final String INSERT_QUERY = "INSERT INTO documents (title, buyerName, buyerEmail, sellerName, sellerEmail, startDate, endDate, risk, buyerPay, sellerPay, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_IDs_QUERY = "SELECT id FROM `jdbc-coursework`.documents";
    private static final String GET_TYPE_QUERY = "SELECT type FROM `jdbc-coursework`.documents WHERE id = ";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM `jdbc-coursework`.documents WHERE id = ";
    private static final String READ_BY_ID = "SELECT * FROM `jdbc-coursework`.documents WHERE id = ";
    private static final String SUM_OF_PROFIT = "SELECT SUM(sellerPay+buyerPay) AS Sum FROM `jdbc-coursework`.documents";
    private static final String SELECT_ALL_TO_TABLE_QUERY = "SELECT id, title, buyerName, sellerName, endDate, risk FROM `jdbc-coursework`.documents";
    private static final String SELECT_OPTIONS_TO_TABLE_QUERY = "SELECT id, title, buyerName, sellerName, endDate, risk FROM `jdbc-coursework`.documents WHERE type = 'O'";
    private static final String SELECT_FUTURES_TO_TABLE_QUERY = "SELECT id, title, buyerName, sellerName, endDate, risk FROM `jdbc-coursework`.documents WHERE type = 'F'";
    private static final String SELECT_ALL_TO_TABLE_SORTED_BY_QUERY = "SELECT id, title, buyerName, sellerName, endDate, risk FROM `jdbc-coursework`.documents ORDER BY risk";
    private static final String UPDATE_QUERY = "UPDATE `jdbc-coursework`.documents SET title = ?, buyerName = ?, buyerEmail = ?, sellerName = ?, sellerEmail = ?, endDate = ?, risk = ? WHERE id = ?;";


    public void insertRecord(PreparedStatement preparedStatement, Document document) throws SQLException {

        preparedStatement.setString(1, document.getTitle());
        preparedStatement.setString(2, document.getBuyer().getFullName());
        preparedStatement.setString(3, document.getBuyer().getEmail());
        preparedStatement.setString(4, document.getSeller().getFullName());
        preparedStatement.setString(5, document.getSeller().getEmail());
        preparedStatement.setDate(6, document.getStartDate());
        preparedStatement.setDate(7, document.getEndDate());
        preparedStatement.setBigDecimal(8, BigDecimal.valueOf(document.getRisk()));
    }

    public void insertOption(Option option) throws SQLException {
        PreparedStatement preparedStatement = JDBCConnection.getConnection().prepareStatement(INSERT_QUERY);

        insertRecord(preparedStatement, option);

        preparedStatement.setBigDecimal(9, BigDecimal.valueOf(option.getBuyerPayment()));
        preparedStatement.setBigDecimal(10, BigDecimal.valueOf(0));
        preparedStatement.setString(11, "O");

        preparedStatement.executeUpdate();
    }

    public void insertFutures(Futures futures) throws SQLException {
        PreparedStatement preparedStatement = JDBCConnection.getConnection().prepareStatement(INSERT_QUERY);

        insertRecord(preparedStatement, futures);

        preparedStatement.setBigDecimal(9, BigDecimal.valueOf(futures.getBuyerPayment()));
        preparedStatement.setBigDecimal(10, BigDecimal.valueOf(futures.getSellerPayment()));
        preparedStatement.setString(11, "F");

        preparedStatement.executeUpdate();

    }

    public ObservableList<TableDto> getAllToTable() {
        return get(SELECT_ALL_TO_TABLE_QUERY);
    }

    public ObservableList<TableDto> getFuturesToTable() {
        return get(SELECT_FUTURES_TO_TABLE_QUERY);
    }

    public ObservableList<TableDto> getOptionsToTable() {
        return get(SELECT_OPTIONS_TO_TABLE_QUERY);
    }

    public ObservableList<TableDto> sortedByRisk() {
        return get(SELECT_ALL_TO_TABLE_SORTED_BY_QUERY);
    }

    public String getTypeById(int id) throws SQLException {
        Statement stmt = JDBCConnection.getConnection().createStatement();

        ResultSet rs = stmt.executeQuery(GET_TYPE_QUERY + id);
        rs.next();
        return rs.getString("type");

    }

    public List<Integer> getIDs() {

        try (Statement stmt = JDBCConnection.getConnection().createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_IDs_QUERY);
            List<Integer> ids = new ArrayList<>();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }

            return ids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<TableDto> get(String query) {

        try (Statement stmt = JDBCConnection.getConnection().createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            List<TableDto> all = new ArrayList<>();

            while (rs.next()) {
                TableDto tableDto = new TableDto(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("buyerName"),
                        rs.getString("sellerName"),
                        rs.getDate("endDate").toString(),
                        rs.getInt("risk"));

                all.add(tableDto);
            }
            stmt.close();
            return FXCollections.observableArrayList(all);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<TableDto> deleteById(int id) {
        try (Statement stmt = JDBCConnection.getConnection().createStatement()) {
            stmt.executeUpdate(DELETE_BY_ID_QUERY + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return get(SELECT_ALL_TO_TABLE_QUERY);
    }

    public Futures readFutures(int id) {
        try (Statement stmt = JDBCConnection.getConnection().createStatement()) {

            ResultSet rs = stmt.executeQuery(READ_BY_ID + id);
            rs.next();

            Document document = readDocument(rs);
            Futures futures = new Futures(document);

            futures.setBuyerPayment(rs.getDouble("buyerPay"));
            futures.setSellerPayment(rs.getDouble("sellerPay"));
            stmt.close();
            return futures;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Option readOption(int id) {
        try (Statement stmt = JDBCConnection.getConnection().createStatement()) {

            ResultSet rs = stmt.executeQuery(READ_BY_ID + id);
            rs.next();

            Document document = readDocument(rs);
            Option option = new Option(document);

            option.setBuyerPayment(rs.getDouble("buyerPay"));
            stmt.close();
            return option;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Document readDocument(ResultSet rs) throws SQLException {
        Person buyer = new Person(rs.getString("buyerName"), rs.getString("buyerEmail"));
        Person seller = new Person(rs.getString("sellerName"), rs.getString("sellerEmail"));
        return new Document(rs.getString("title"), buyer, seller, rs.getDate("startDate"), rs.getDate("endDate"), rs.getDouble("risk"));
    }

    public void updateDocument(Document document, int id) {

        try (PreparedStatement preparedStatement = JDBCConnection.getConnection().prepareStatement(UPDATE_QUERY)) {

            preparedStatement.setString(1, document.getTitle());
            preparedStatement.setString(2, document.getBuyer().getFullName());
            preparedStatement.setString(3, document.getBuyer().getEmail());
            preparedStatement.setString(4, document.getSeller().getFullName());
            preparedStatement.setString(5, document.getSeller().getEmail());
            preparedStatement.setDate(6, document.getEndDate());
            preparedStatement.setBigDecimal(7, BigDecimal.valueOf(document.getRisk()));
            preparedStatement.setInt(8, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculateProfit() {
        try (Statement stmt = JDBCConnection.getConnection().createStatement()) {

            ResultSet rs = stmt.executeQuery(SUM_OF_PROFIT);
            rs.next();

            double sum = rs.getInt(1);

            stmt.close();
            return sum;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
