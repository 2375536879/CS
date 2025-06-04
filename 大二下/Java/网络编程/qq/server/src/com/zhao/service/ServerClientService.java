package com.zhao.service;

import com.zhao.common.Message;
import com.zhao.common.MessageType;
import com.zhao.dao.MessageDao;
import com.zhao.dao.UserDao;
import com.zhao.po.User;
import com.zhao.util.SocketUtil;
import com.zhao.util.TalkThread;
import com.zhao.util.TalkThreadCache;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

//给服务提供服务
public class ServerClientService {

    private static final int PORT = 8888;
    UserDao userDao = new UserDao();
    MessageDao messageDao = new MessageDao();
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

                case MessageType.TALK_CONNECTION:{

                    TalkThread talkThread=new TalkThread(socket);
                    talkThread.start();
                    TalkThreadCache.talkThreadCache.put(requestMessage.getUserName()+"-"+requestMessage.getFriendName(),talkThread);

                    //从数据库查询未读的朋友留言
                    //注意这里的顺序
                    //my给test发，test不在线。这是test登上之后建立的socket连接，此时test（requestMessage.getFriendName()）作为UserName,my（requestMessage.getUserName()）作为FriendName
                    List<Message>messages=messageDao.getMessages(requestMessage.getFriendName(),requestMessage.getUserName());
                    for(Message message:messages){
                        message.setIsRead(1);
                    }
                    if(messages!=null&&messages.size()>0){
                        Socket currentSocket=socket;
                        Message responseMessage=new Message();
                        responseMessage.setMsgType(MessageType.TALK_LEAVING);
                        responseMessage.setMessages(messages);
                        //将留言信息返回给客户端
                        SocketUtil.getInstance().sendMessgae(currentSocket, responseMessage);
                    }
                    break;
                }

            }


        }

    }


}
