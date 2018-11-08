package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);

		FontType font = new FontType(loader.loadFontTextureAtlas("candara"), new File("res/fonts/candara.fnt"));
		GUIText text = new GUIText("A small part of AUI campus!", 3, font, new Vector2f(0.0f, 0.8f), 1f, true);
		text.setColour(0.1f, 0.1f, 0.1f);

		// *********TERRAIN TEXTURE STUFF**********

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// *****************************************

		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();				

		ModelTexture AUIBuildingTextureAtlas = new ModelTexture(loader.loadTexture("AUIBuilding"));
		AUIBuildingTextureAtlas.setNumberOfRows(1);
		TexturedModel AUIBuilding = new TexturedModel(OBJFileLoader.loadOBJ("AUIBuilding", loader), AUIBuildingTextureAtlas);

		ModelTexture AUIBuildingWOCaffetTextureAtlas = new ModelTexture(loader.loadTexture("AUIBuildingWithoutCaffeteria"));
		AUIBuildingWOCaffetTextureAtlas.setNumberOfRows(1);
		TexturedModel AUIBuildingWOCaffet = new TexturedModel(OBJFileLoader.loadOBJ("AUIBuilding", loader), AUIBuildingWOCaffetTextureAtlas);		
		
		// AUIBuildingWithoutCaffeteria
		
		
		ModelTexture AUIFountainTextureAtlas = new ModelTexture(loader.loadTexture("AUIFountain"));
		AUIFountainTextureAtlas.setNumberOfRows(1);
		TexturedModel AUIFountain = new TexturedModel(OBJFileLoader.loadOBJ("AUIFountain", loader), AUIFountainTextureAtlas);
		
//		AUIBuilding.getTexture().setHasTransparency(true);
					
		entities.add(new Entity(AUIBuildingWOCaffet, 1, new Vector3f(50 * 4, 0, -28.53f * 4),
				0,90,0, 20));
		entities.add(new Entity(AUIBuilding, 2, new Vector3f((50 + 0.18f) * 4, 0, -71.255f * 4),
				0,-90,0, 20));
		entities.add(new Entity(AUIBuildingWOCaffet, 3, new Vector3f(100 * 4, 0, -50 * 4),
				0,180,0, 20));
		entities.add(new Entity(AUIFountain, 4, new Vector3f(72.5f * 4, 0.2f, -50 * 4),
				0,180,0, 20));
		
			
//		entities.add(new Entity(FountainTexturedModel, new Vector3f(72.5f, 0, 50),
//				0,180,0, 5));		
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);

		// *******************OTHER SETUP***************

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(10000, 10000, 10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);

		MasterRenderer renderer = new MasterRenderer(loader);

		RawModel bunnyModel = OBJLoader.loadObjModel("player", loader);
		TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("playerTexture")));

		Player player = new Player(stanfordBunny, new Vector3f(72.5f * 4, 0.2f, -50 * 4), 0, 100, 0, 0.6f);
		entities.add(player);
		Camera camera = new Camera(player);
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

		// **********Water Renderer Set-up************************

		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(327, -200, 1);
		waters.add(water);
		WaterTile water2 = new WaterTile(327.5f, -200, 2);
		water2.TILE_SIZE = 6.7f;
		waters.add(water2);
		
		WaterTile water3 = new WaterTile(318.5f, -200, 2);
		water3.TILE_SIZE = 1f;
		waters.add(water3);
		WaterTile water4 = new WaterTile(320.25f, -200, 2);
		water4.TILE_SIZE = 0.75f;
		waters.add(water4);
		WaterTile water5 = new WaterTile(319f, -201.5f, 2);
		water5.TILE_SIZE = 0.5f;
		waters.add(water5);	
		WaterTile water6 = new WaterTile(319f, -198.5f, 2);
		water6.TILE_SIZE = 0.5f;
		waters.add(water6);
		WaterTile water7 = new WaterTile(320.25f, -201.5f, 2);
		water7.TILE_SIZE = 0.8f;
		waters.add(water7);	
		WaterTile water8 = new WaterTile(320.25f, -202.5f, 2);
		water8.TILE_SIZE = 0.6f;
		waters.add(water8);		
		WaterTile water9 = new WaterTile(320.25f, -198.5f, 2);
		water9.TILE_SIZE = 0.8f;
		waters.add(water9);	
		WaterTile water10 = new WaterTile(320.25f, -197.5f, 2);
		water10.TILE_SIZE = 0.6f;
		waters.add(water10);			

		
		WaterTile water11 = new WaterTile(336.5f, -200, 2);
		water11.TILE_SIZE = 1f;
		waters.add(water11);
		WaterTile water12 = new WaterTile(334.75f, -200, 2);
		water12.TILE_SIZE = 0.75f;
		waters.add(water12);
		WaterTile water13 = new WaterTile(334.5f, -202.5f, 2);
		water13.TILE_SIZE = 0.5f;
		waters.add(water13);	
		WaterTile water14 = new WaterTile(334.5f, -197.5f, 2);
		water14.TILE_SIZE = 0.5f;
		waters.add(water14);
		WaterTile water15 = new WaterTile(335f, -201.5f, 2);
		water15.TILE_SIZE = 0.9f;
		waters.add(water15);	
		WaterTile water16 = new WaterTile(335.5f, -200.5f, 2);
		water16.TILE_SIZE = 0.95f;
		waters.add(water16);		
		WaterTile water17 = new WaterTile(335f, -198.5f, 2);
		water17.TILE_SIZE = 0.9f;
		waters.add(water17);	
		WaterTile water18 = new WaterTile(335.5f, -199f, 2);
		water18.TILE_SIZE = 0.95f;
		waters.add(water18);	
		
		
		WaterTile water19 = new WaterTile(327.25f, -191, 2);
		water19.TILE_SIZE = 1f;
		waters.add(water19);
		WaterTile water20 = new WaterTile(327.25f, -192.75f, 2);
		water20.TILE_SIZE = 0.75f;
		waters.add(water20);
		WaterTile water21 = new WaterTile(325f, -193f, 2);
		water21.TILE_SIZE = 0.5f;
		waters.add(water21);	
		WaterTile water22 = new WaterTile(326f, -192.35f, 2);
		water22.TILE_SIZE = 0.95f;
		waters.add(water22);
		WaterTile water23 = new WaterTile(329f, -192.75f, 2);
		water23.TILE_SIZE = 1f;
		waters.add(water23);	
		WaterTile water24 = new WaterTile(328.3f, -191.3f, 2);
		water24.TILE_SIZE = 0.5f;
		waters.add(water24);		
		WaterTile water25 = new WaterTile(335f, -192.75f, 2);
		water25.TILE_SIZE = 0.9f;
//		waters.add(water25);	
		
		
		WaterTile water26 = new WaterTile(327.25f, -208.9f, 2);
		water26.TILE_SIZE = 1f;
		waters.add(water26);	
		WaterTile water27 = new WaterTile(327.25f, -207.25f, 2);
		water27.TILE_SIZE = 1f;
		waters.add(water27);
		WaterTile water28 = new WaterTile(329.15f, -207.25f, 2);
		water28.TILE_SIZE = 0.95f;
		waters.add(water28);
		WaterTile water29 = new WaterTile(325.5f, -207.25f, 2);
		water29.TILE_SIZE = 0.95f;
		waters.add(water29);
		WaterTile water30 = new WaterTile(328.65f, -208.25f, 2);
		water30.TILE_SIZE = 0.5f;
		waters.add(water30);
		WaterTile water31 = new WaterTile(326f, -208.25f, 2);
		water31.TILE_SIZE = 0.5f;
		waters.add(water31);
		
		
		WaterTile water32 = new WaterTile(304f, -200, 1);
		water32.TILE_SIZE = 8;
		waters.add(water32);	
		WaterTile water33 = new WaterTile(288f, -200, 1);
		water33.TILE_SIZE = 8;
		waters.add(water33);
		WaterTile water34 = new WaterTile(282f, -200, 1);
		water34.TILE_SIZE = 8;
		waters.add(water34);
		WaterTile water35 = new WaterTile(276f, -200, 1);
		water35.TILE_SIZE = 8;
		waters.add(water35);
		WaterTile water36 = new WaterTile(270f, -200, 1);
		water36.TILE_SIZE = 8;
		waters.add(water36);
		WaterTile water37 = new WaterTile(264f, -200, 1);
		water37.TILE_SIZE = 8;
		waters.add(water37);
		WaterTile water38 = new WaterTile(258f, -200, 1);
		water38.TILE_SIZE = 8;
		waters.add(water38);
		WaterTile water39 = new WaterTile(252f, -200, 1);
		water39.TILE_SIZE = 8;
		waters.add(water39);
		WaterTile water40 = new WaterTile(246f, -200, 1);
		water40.TILE_SIZE = 8;
		waters.add(water40);	
		WaterTile water41 = new WaterTile(240f, -200, 1);
		water41.TILE_SIZE = 8;
		waters.add(water41);	
		
		
		WaterTile water42 = new WaterTile(228f, -195, 1);
		water42.TILE_SIZE = 10;
		waters.add(water42);
		WaterTile water43 = new WaterTile(228f, -205, 1);
		water43.TILE_SIZE = 10;
		waters.add(water43);		
		
		WaterTile water44 = new WaterTile(170f, -195, 1);
		water44.TILE_SIZE = 10;
		waters.add(water44);
		WaterTile water45 = new WaterTile(170f, -205, 1);
		water45.TILE_SIZE = 10;
		waters.add(water45);			
		
		
		WaterTile water46 = new WaterTile(152f, -200, 1);
		water46.TILE_SIZE = 8;
		waters.add(water46);	
		WaterTile water47 = new WaterTile(136f, -200, 1);
		water47.TILE_SIZE = 8;
		waters.add(water47);
		WaterTile water48 = new WaterTile(130f, -200, 1);
		water48.TILE_SIZE = 8;
		waters.add(water48);
		WaterTile water49 = new WaterTile(124f, -200, 1);
		water49.TILE_SIZE = 8;
		waters.add(water49);
		WaterTile water50 = new WaterTile(118f, -200, 1);
		water50.TILE_SIZE = 8;
		waters.add(water50);
		WaterTile water51 = new WaterTile(112f, -200, 1);
		water51.TILE_SIZE = 8;
		waters.add(water51);
		WaterTile water52 = new WaterTile(106f, -200, 1);
		water52.TILE_SIZE = 8;
		waters.add(water52);
		WaterTile water53 = new WaterTile(100f, -200, 1);
		water53.TILE_SIZE = 8;
		waters.add(water53);
		WaterTile water54 = new WaterTile(94f, -200, 1);
		water54.TILE_SIZE = 8;
		waters.add(water54);	
		WaterTile water55 = new WaterTile(88f, -200, 1);
		water55.TILE_SIZE = 8;
		waters.add(water55);	
		
		WaterTile water56 = new WaterTile(82f, -200, 1);
		water56.TILE_SIZE = 8;
		waters.add(water56);	
		WaterTile water57 = new WaterTile(76f, -200, 1);
		water57.TILE_SIZE = 8;
		waters.add(water57);
		WaterTile water58 = new WaterTile(70f, -200, 1);
		water58.TILE_SIZE = 8;
		waters.add(water58);	
		WaterTile water59 = new WaterTile(64f, -200, 1);
		water59.TILE_SIZE = 8;
		waters.add(water59);	
		WaterTile water60 = new WaterTile(61f, -200, 1);
		water60.TILE_SIZE = 8;
		waters.add(water60);			
		
		
		// ****************Game Loop Below*********************

		while (!Display.isCloseRequested()) {
			player.move(terrain);
			camera.move();
			picker.update();
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

			// render reflection texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight() + 1));
			camera.getPosition().y += distance;
			camera.invertPitch();

			// render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));

			// render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
			waterRenderer.render(waters, camera, sun);
			guiRenderer.render(guiTextures);
			TextMaster.render();

			DisplayManager.updateDisplay();
		}

		// *********Clean Up Below**************

		TextMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
