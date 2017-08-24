/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 *
 * @author Rossco
 */
public class RSA
{

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args)
    {
        BigInteger ONE=new BigInteger ("1");
        SecureRandom rand=new SecureRandom ();

        BigInteger d, e, n;
        //picking p and q, to generate n the world in which lock will take place
        //both must be prime and p != q
        BigInteger p=BigInteger.probablePrime (2048, rand);
        BigInteger q=BigInteger.probablePrime (2048, rand);
        System.out.println (p);
        System.out.println (q);
        n=p.multiply (q);
        System.out.println ("the world in which encryption will take place is " + n);

        // creating phi world  or alternate world by taking 1 away from p and q and multiplying
        BigInteger phi=(p.subtract (ONE)).multiply (q.subtract (ONE));
        System.out.println (p.subtract (ONE));
        System.out.println ("the phi world in which encryption will take place is " + phi);
        //destroying p and q so we cant get bak to the phi world
        p=null;
        q=null;

        //we pick e ensuring it is co primee so has unlock pair
        e=new BigInteger ("1127369");
        // create d using extened euclydian algorithim
        // we find the unlock pair(d) of e in the phi word
        d=e.modInverse (phi);
        System.out.println ("the unlock key d is " + d);
        // implementing an example in a message
        BigInteger bi3;
        bi3=e.gcd (phi);
        String str="GCD of " + e + " and " + phi + " is " + bi3;

        // print bi3 value
        System.out.println (str);

        while (bi3.equals (ONE))
        {
            Scanner kb=new Scanner (System.in);
            System.out.println ("please enter the message to send");
            String message=kb.nextLine ();

            // converting message to byte array 
            BigInteger plainText=new BigInteger (message.getBytes ());
            //  encrypting message into cypyer text 
            System.out.println ("the plaintext is " + plainText);
            BigInteger cipherText=plainText.modPow (e, n);
            System.out.println ("the cypertext is " + cipherText);
            //  decrypt message back using d the unlock
            BigInteger originalMessage=cipherText.modPow (d, n);
            System.out.println ("original message is " + originalMessage);
            //  message is converted back to a string using byte array
            String decrypted=new String (originalMessage.toByteArray ());

            System.out.println ("The original message sent was: " + message);
            System.out.println ("The decrypted message is : " + decrypted);
        }
    }

}
