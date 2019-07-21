package com.xzy.utils.file.bean;

import com.xzy.utils.pinyin.PinyinUtils;

/**
 * 音乐 bean
 *
 * @author xzy
 */
@SuppressWarnings("all")
public class Music implements Comparable<Music> {
    /**
     * 歌曲名
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 所属专辑
     */
    private String album;
    /**
     * 艺术家(作者)
     */
    private String artist;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 时长
     */
    private int duration;

    private String pinyin;

    public Music(String name, String path, String album,
                 String artist, long size, int duration) {
        this.name = name;
        this.path = path;
        this.album = album;
        this.artist = artist;
        this.size = size;
        this.duration = duration;
        pinyin = PinyinUtils.getPinyin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(Music music) {
        return this.pinyin.compareTo(music.getPinyin());
    }

    @Override
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
