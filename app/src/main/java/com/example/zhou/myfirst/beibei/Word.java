package com.example.zhou.myfirst.beibei;

//单词类
public class Word {
    public int _id;
    public String en_meaning;
    public String cn_meaning;
    public int repeat_number;

    public Word(){}

    public Word(String en_meaning,String cn_meaning,int repeat_number)
    {
        this.en_meaning=en_meaning;
        this.cn_meaning=cn_meaning;
        this.repeat_number=repeat_number;
    }
}
