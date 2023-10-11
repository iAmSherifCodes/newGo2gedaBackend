package com.go2geda.Go2GedaApp.walletTest;

import com.go2geda.Go2GedaApp.data.models.Role;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.data.models.Wallet;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WalletTest {
    public void testToCreateAWallet(){
        User user =new User();
        user.setRole(Role.DRIVER);


        Wallet wallet = new Wallet();
        wallet.setWalletPin("123456");
        wallet.setWalletBVN("12345678");
      wallet.setWalletBank("345689087");


    }



}
