package me.mrmaurice.merryskin;

public enum HeadEnum {

	SANTA_HAT("Layouts/SantaHat.png", "/images/SantaHat.png"),
	SNOWMAN("Layouts/SnowmanHat.png", "/images/SnowMan.png"),
	GIFT("Layouts/GiftHead.png", "/images/Gift.png"),
	GINGERBREAD("Layouts/GingerHead.png", "/images/Ginger.png"),
	ELF("Layouts/ElfHead.png", "/images/Elf.png"),
	REINDEER("Layouts/ReindeerHat.png", "/images/Reno.png"),
	CUSTOM("Layouts/CustomHat.png", "/images/CustomHat.png");
	
	private String path;
	private String innerPath;
	
	private HeadEnum(String path, String innerPath){
		this.path = path;
		this.innerPath = innerPath;
	}
	
	public String getPath(){
		return path;
	}
	
	public String getInnerPath(){
		return innerPath;
	}
	
}
