package udpm.fpt.event;

public interface TableActionEvent {

    public void onRestore(int row);

    public void onDelete(int row);

    public void onView(int row);

}
