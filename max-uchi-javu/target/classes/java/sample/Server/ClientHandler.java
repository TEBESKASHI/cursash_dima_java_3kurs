package sample.Server;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

import static java.lang.System.exit;


public class ClientHandler {
    private Server server;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nick;
    private boolean isAuth;
    private DatabaseHandler db;

    public ClientHandler(Server srv, Socket sock) {
        try {
            db = new DatabaseHandler();
            this.server = srv;
            this.socket = sock;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.isAuth = false;
            new Thread(() -> {
                System.out.println("Пользователь подключен: " + socket.getInetAddress() + ":" + socket.getPort() + "(" + socket.getLocalPort() + ")");
                try {
                    DataObject d;
                    try {
                        while(true) {
                            d = (DataObject) in.readObject();
                            if (d.getCommand().equals("Enter")) {
                                d = checkEnter(d);
                                System.out.println(d.getLogin());
                                sendMsg(d);
                            } else if (d.getCommand().equals("Reg")) {
                                d = regUser(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("addCompany")){
                                d = addCompany(d);
                                sendMsg(d);
                            }
                            else if(d.getCommand().equals("getCompany")) {
                                DataObject[] arr;
                                arr = getCompany(d);
                                sendBigMsg(arr);
                            }
                            else if (d.getCommand().equals("editCompany")) {
                                editCompany(d);
                            }
                            else if (d.getCommand().equals("deleteCompany")) {
                                deleteCompany(d);
                            }
                            else if (d.getCommand().equals("addCapital")) {
                                addCapital(d);
                            }
                            else if (d.getCommand().equals("addBorrowedCapital")) {
                                addBorrowedCapital(d);
                            }
                            else if (d.getCommand().equals("editCapital")) {
                                editCapital(d);
                            }
                            else if (d.getCommand().equals("editBorrowedCapital")) {
                                editBorrowedCapital(d);
                            }
                            else if (d.getCommand().equals("confirmRisk")) {
                                d = confirmRisk(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("riskGetInfo")) {
                                d = riskGetInfo(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("diagCircle")) {
                                d = diagCircle(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("diagLine")) {
                                d = diagLine(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("margin")) {
                                d= margin(d);
                                sendMsg(d);
                            }
                            else if (d.getCommand().equals("bisnes")) {
                                d = bisnes(d);
                                sendMsg(d);
                            }
                            System.out.println("Пользователь отключен: операция прошла успешно");
                        }
                    } catch (EOFException e) {

                    } catch (SocketException e) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(DataObject msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendBigMsg(DataObject[] msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataObject checkEnter(DataObject msg) {
        return db.ChechEnter(msg);
    }

    private DataObject regUser(DataObject msg){
        return db.RegUser(msg);
    }

private DataObject bisnes(DataObject msg) { return db.bisnes(msg); };

    private DataObject confirmRisk(DataObject msg) throws  SQLException { return db.confirmRisk(msg);}
//
   private DataObject[] getCompany(DataObject msg) throws SQLException { return db.getCompany(msg); }
//
//
    private void editCapital(DataObject msg) { db.editCapital(msg);}

    private DataObject addCompany(DataObject msg) throws SQLException { return db.addCompany(msg);}
//
    private void editCompany(DataObject msg){ db.editCompany(msg);}
//
   private void deleteCompany(DataObject msg){ db.deleteCompany(msg);}

   private DataObject riskGetInfo(DataObject msg) { return db.riskGetInfo(msg); };

   private void addCapital(DataObject msg) { db.addCapital(msg);}

   private void addBorrowedCapital(DataObject msg)  { db.addBorrowedCapital(msg);};

   private void editBorrowedCapital(DataObject msg) { db.editBorrowedCapital(msg);};

   private DataObject diagLine(DataObject msg) { return db.diagLine(msg); };

  //  private void delUser(DataObject msg){ db.DelUser(msg);}

    private DataObject diagCircle(DataObject msg) { return db.diagCircle(msg); }

    private DataObject margin(DataObject  msg) { return db.margin(msg); }
}