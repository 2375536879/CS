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

    public boolean deleteReader(int readerId) {
        // 检查读者是否存在
        Reader reader = readerDao.getReader(readerId);
        if (reader == null) {
            return false;
        }
        
        // 检查读者是否有未归还的图书
        if (readerDao.hasUnreturnedBooks(readerId)) {
            JOptionPane.showMessageDialog(null, "该读者还有未归还的图书，无法删除！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return readerDao.deleteReader(readerId);
    }
}
