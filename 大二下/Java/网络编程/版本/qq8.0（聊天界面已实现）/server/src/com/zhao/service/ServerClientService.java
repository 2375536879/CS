package com.zhao.service;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.dao.UserDao;
import com.zhao.po.User;
import com.zhao.util.SocketUtil;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

//给服务提供服务
public class ServerClientService {

    private static final int PORT = 8888;
    UserDao userDao = new UserDao();

    //启动服务端的服务，等待客户端连接
    public  void startServer()throws Exception{

        //服务监听在8888端口上
        ServerSocket serverSocket = new ServerSocket(PORT);

        //通过循环来保障服务端一直有连接
        while(true){
            Socket socket= serverSocket.accept();

            //接收客户端的信息
            //首先发来的是requestMessage,不是responseMessage,不要搞错了
            Message requestMessage= SocketUtil.getInstance().getMessgae(socket);//客户端发来的请求

            switch(requestMessage.getMsgType()){
                case MessageType.LOGIN:{
                    User user= requestMessage.getUser();
                    //根据输入的账号和密码确定message里面的内容
                    Message message=new Message();
                    //if(user.getUsername().equals("root")&&user.getPwd().equals("123456")){
                    if(userDao.login(user.getUsername(),user.getPwd())!=null){//查到了
                        message.setMsgType(MessageType.LOGIN_SUCCESS);
                    }else{
                        message.setMsgType(MessageType.LOGIN_FAIL);
                    }
                    //向客户端输出验证结果
                    SocketUtil.getInstance().sendMessgae(socket, message);
                    break;
                }

                case MessageType.REGISTER:{
                    //向客户端应答的message先new出来
                    Message responseMessage=new Message();
                    //通过客户端的请求message获取到注册的user信息
                    User user= requestMessage.getUser();

                    //通过getByUsername看是否Username已经被注册
                    if(userDao.getByUsername(user.getUsername())==null){
                        //将user信息存入数据库
                        userDao.insertUser(user);
                        responseMessage.setMsgType(MessageType.REGISTER_SUCCESS);
                    }else{
                        responseMessage.setMsgType(MessageType.REGISTER_FAIL);
                    }
                    //向客户端输出验证结果
                    SocketUtil.getInstance().sendMessgae(socket, responseMessage);

                    break;
                }

                case MessageType.GET_USERS:{
                    //向客户端应答的message先new出来
                    Message responseMessage=new Message();
                    List<User> users=userDao.getUsers();
                    responseMessage.setUsers(users);

                    //向客户端输出验证结果
                    SocketUtil.getInstance().sendMessgae(socket, responseMessage);
                    break;
                }

            }


        }

    }


}
