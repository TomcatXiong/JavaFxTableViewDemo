package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Controller
 *
 * @author yx
 * @date 2019/10/07 16:04
 */
public class Controller {
    @FXML
    private Button mBtnDelete;

    @FXML
    private TableView mTable;

    @FXML
    private TextField mTxtName;

    @FXML
    private TextField mTxtSex;

    @FXML
    private TableColumn<Student, Boolean> mColumnSelect;

    @FXML
    private TableColumn<Student, String> mColumnName;

    @FXML
    private TableColumn<Student, String> mColumnSex;

    private final ObservableList<Student> data =
            FXCollections.observableArrayList();

    @FXML
    public void onDelete(ActionEvent event) {
        deleteStudents();
    }

    @FXML
    public void onAdd(ActionEvent event) {
        if (mTxtName.getText() != null && mTxtSex.getText() != null) {
            data.add(new Student(
                    mTxtName.getText(),
                    mTxtSex.getText()));
            mTxtName.clear();
            mTxtSex.clear();
        }
    }

    private boolean deleteStudents() {
        int size = data.size();
        if (size <= 0) {
            return false;
        }
        for (int i = size - 1; i >= 0; i--) {
            Student s = data.get(i);
            if (s.getSelected()) {
                data.remove(s);
            }
        }
        return true;
    }

    @FXML
    private void initialize() {
        mColumnSelect.setCellValueFactory(new PropertyValueFactory<Student, Boolean>("selected"));
        mColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mColumnSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        mTable.setItems(data);
        mTable.setEditable(true);

        mColumnSelect.setCellFactory(
                CellFactory.tableCheckBoxColumn(new Callback<Integer, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(Integer index) {
                        final Student g = (Student) mTable.getItems().get(index);
                        ObservableValue<Boolean> ret =
                                new SimpleBooleanProperty(g, "selected", g.getSelected());
                        ret.addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(
                                    ObservableValue<? extends Boolean> observable,
                                    Boolean oldValue, Boolean newValue) {
                                g.setSelected(newValue);
                            }
                        });
                        return ret;
                    }
                }));

        Callback<TableColumn<Student, String>,
                TableCell<Student, String>> cellFactory
                = (TableColumn<Student, String> p) -> new EditingCell();
        mColumnName.setCellFactory(cellFactory);
        mColumnSex.setCellFactory(cellFactory);
        mColumnName.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });

        mColumnSex.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setSex(t.getNewValue());
                });

    }

}
