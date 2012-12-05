RSATool
=======

RSATool for generating, storing keys, encrypting and decrypting files.

I need to work more on this Project. Some simple things like cleaning up the code and a bit of reformatting.
I will create a better read-me within time.

Please feel free to optimize this code.

I've kept in mind that one might wish to encrypt/decrypt with any of the key part.

It uses Blowfish to encrypt the keys. Blowfish accepts MD5 hash of the user-chosen passpharse.

I have taken Seeder class and Counter class from one of the books. I've never ever worked with
Java GUI programming. Therefore, I cannot make a full use of the classes. Everyone is welcome to
edit this too.

Thank You!

Here is how to use it:
----------------------------------------------------------------------------------------------
Command line options:

-gen : To generate a new RSA Key pair.
        Provide a password when asked. It will be used as a passphrase to store encrypted version of your public as well as private keys.
        Your security of the keys depends upon it.
        Optimization: It encrypts Public Key too. Needs to change? Please suggest!
        
-enc <filename>: To encrypt the given file.
        It will ask for the passphrase, key and public/private part of the key. It will then generate a new file which is encrypted with
        the provided details.
        
-dec <filename>: To decrypt the given file.
        It will ask for the passphrase, key and public/private part of the key. It will then generate a new file which is the original version
        that is decrypted version of the encrypted file.
        
------------------------------------------------------------------------------------------------