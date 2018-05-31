package com.hotservice.sauron.utils;

import android.util.Log;

import com.hotservice.sauron.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class UserHelper {
    public byte[] toBytes(User m) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(m);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            Log.d(this.getClass().getSimpleName(), e.toString());
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                Log.d(this.getClass().getSimpleName(), e.toString());
            }

        }
        return null;
    }

    public User toUser(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (User) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.d(this.getClass().getSimpleName(), e.toString());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                Log.d(this.getClass().getSimpleName(), e.toString());
            }
        }
        return null;
    }
}
