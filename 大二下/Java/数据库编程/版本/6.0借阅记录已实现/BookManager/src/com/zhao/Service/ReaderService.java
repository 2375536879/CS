package com.zhao.Service;

import com.zhao.dao.ReaderDao;
import com.zhao.po.Reader;
import com.zhao.view.ReaderView;

import javax.swing.*;

public class ReaderService {
    ReaderDao readerDao=new ReaderDao();

    public boolean addReader(Reader reader){
      if(readerDao.addReader(reader)==true){
          return true;
      }else
          return false;
    }

}
