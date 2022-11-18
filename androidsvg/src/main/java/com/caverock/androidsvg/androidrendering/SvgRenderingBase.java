/*
   Copyright 2013-2020 Paul LeBeau, Cave Rock Software Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.caverock.androidsvg.androidrendering;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.Log;

import com.caverock.androidsvg.model.PreserveAspectRatio;
import com.caverock.androidsvg.loader.SVGExternalFileResolver;
import com.caverock.androidsvg.model.Box;
import com.caverock.androidsvg.model.Length;
import com.caverock.androidsvg.model.PathDefinition;
import com.caverock.androidsvg.model.Rule;
import com.caverock.androidsvg.model.Source;
import com.caverock.androidsvg.model.Svg;
import com.caverock.androidsvg.model.SvgContainer;
import com.caverock.androidsvg.model.SvgElementBase;
import com.caverock.androidsvg.model.SvgObject;
import com.caverock.androidsvg.model.Unit;
import com.caverock.androidsvg.model.View;
import com.caverock.androidsvg.parser.ParserHelper;
import com.caverock.androidsvg.parser.SVGParseException;
import com.caverock.androidsvg.model.SvgStructure;
import com.caverock.androidsvg.parser.SVGParser;
import com.caverock.androidsvg.model.Ruleset;
import com.caverock.androidsvg.parser.SVGParserFactoryImpl;
import com.caverock.androidsvg.util.AndroidLogger;
import com.caverock.androidsvg.util.Logger;
import com.caverock.androidsvg.xml.AutomaticAndroidXmlParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AndroidSVG is a library for reading, parsing and rendering SVG documents on Android devices.
 * <p>
 * All interaction with AndroidSVG is via this class.
 * <p>
 * Typically, you will call one of the SVG loading and parsing classes then call the renderer,
 * passing it a canvas to draw upon.
 * 
 * <h3>Usage summary</h3>
 * 
 * <ul>
 * <li>Use one of the static {@code getFromX()} methods to read and parse the SVG file.  They will
 * return an instance of this class.
 * <li>Call one of the {@code renderToX()} methods to render the document.
 * </ul>
 * 
 * <h3>Usage example</h3>
 * 
 * <pre>
 * {@code
 * SVG.registerExternalFileResolver(myResolver);
 *
 * SVG  svg = SVG.getFromAsset(getContext().getAssets(), svgPath);
 *
 * Bitmap  newBM = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
 * Canvas  bmcanvas = new Canvas(newBM);
 * bmcanvas.drawRGB(255, 255, 255);  // Clear background to white
 *
 * svg.renderToCanvas(bmcanvas);
 * }
 * </pre>
 * 
 * For more detailed information on how to use this library, see the documentation at {@code http://code.google.com/p/androidsvg/}
 */

public class SvgRenderingBase implements SvgStructure {
   //static final String  TAG = "SVGBase";

   private static final int     DEFAULT_PICTURE_WIDTH = 512;
   private static final int     DEFAULT_PICTURE_HEIGHT = 512;

   // Parser configuration singletons
   // Configures the parser that will be used for the next SVG that gets parsed
   private static SVGExternalFileResolver  externalFileResolverSingleton = null;
   private static boolean                  enableInternalEntitiesSingleton = true;

   // The parser configuration settings that was used for the current instance
   // WIll continue to be used for future parsing by this instance. For example
   // when parsing addition CSS.
   private final SVGExternalFileResolver  externalFileResolver;
   private final boolean                  enableInternalEntities;

   // The root svg element
   private Svg rootElement = null;

   // Metadata
   private String  title = "";
   private String  desc = "";

   // DPI to use for rendering
   private float   renderDPI = 96f;   // default is 96

   // CSS rules
   private final Ruleset  cssRules = new Ruleset();

   // Map from id attribute to element
   private final Map<String, SvgElementBase> idToElementMap = new HashMap<>();


   /* package private */
   public SvgRenderingBase(boolean enableInternalEntities, SVGExternalFileResolver fileResolver)
   {
      this.enableInternalEntities = enableInternalEntities;
      this.externalFileResolver = fileResolver;
   }


   /**
    * Read and parse an SVG from the given {@code InputStream}.
    *
    * @param is the input stream from which to read the file.
    * @return an SVG instance on which you can call one of the render methods.
    * @throws SVGParseException if there is an error parsing the document.
    */
   @SuppressWarnings("WeakerAccess")
   public static SvgRenderingBase getFromInputStream(InputStream is) throws SVGParseException
   {
      return (SvgRenderingBase) createParser().parseStream(is);
   }


   /**
    * Read and parse an SVG from the given {@code String}.
    *
    * @param svg the String instance containing the SVG document.
    * @return an SVG instance on which you can call one of the render methods.
    * @throws SVGParseException if there is an error parsing the document.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public static SvgRenderingBase getFromString(String svg) throws SVGParseException
   {
      return (SvgRenderingBase) createParser().parseStream(new ByteArrayInputStream(svg.getBytes()));
   }


   /**
    * Read and parse an SVG from the given resource location.
    *
    * @param context the Android context of the resource.
    * @param resourceId the resource identifier of the SVG document.
    * @return an SVG instance on which you can call one of the render methods.
    * @throws SVGParseException if there is an error parsing the document.
    */
   @SuppressWarnings("WeakerAccess")
   public static SvgRenderingBase getFromResource(Context context, int resourceId) throws SVGParseException
   {
      return getFromResource(context.getResources(), resourceId);
   }


   /**
    * Read and parse an SVG from the given resource location.
    *
    * @param resources the set of Resources in which to locate the file.
    * @param resourceId the resource identifier of the SVG document.
    * @return an SVG instance on which you can call one of the render methods.
    * @throws SVGParseException if there is an error parsing the document.
    * @since 1.2.1
    */
   @SuppressWarnings("WeakerAccess")
   public static SvgRenderingBase getFromResource(Resources resources, int resourceId) throws SVGParseException
   {
      InputStream  is = resources.openRawResource(resourceId);
      //noinspection TryFinallyCanBeTryWithResources
      try {
         return (SvgRenderingBase) createParser().parseStream(is);
      } finally {
         try {
           is.close();
         } catch (IOException e) {
           // Do nothing
         }
      }
   }


   /**
    * Read and parse an SVG from the assets folder.
    *
    * @param assetManager the AssetManager instance to use when reading the file.
    * @param filename the filename of the SVG document within assets.
    * @return an SVG instance on which you can call one of the render methods.
    * @throws SVGParseException if there is an error parsing the document.
    * @throws IOException if there is some IO error while reading the file.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public static SvgRenderingBase getFromAsset(AssetManager assetManager, String filename) throws SVGParseException, IOException
   {
      InputStream  is = assetManager.open(filename);
      //noinspection TryFinallyCanBeTryWithResources
      try {
         return (SvgRenderingBase) createParser().parseStream(is);
      } finally {
         try {
           is.close();
         } catch (IOException e) {
           // Do nothing
         }
      }
   }


   /**
    * Parse an SVG path definition from the given {@code String}.
    *
    * {@code
    * Path  path = SVG.parsePath("M 0,0 L 100,100");
    * path.setFillType(Path.FillType.EVEN_ODD);
    *
    * // You could render the path to a Canvas now
    * Paint paint = new Paint();
    * paint.setStyle(Paint.Style.FILL);
    * paint.setColor(Color.RED);
    * canvas.drawPath(path, paint);
    *
    * // Or perform other operations on it
    * RectF bounds = new RectF();
    * path.computeBounds(bounds, false);
    * }
    *
    * Note that this method does not throw any exceptions or return any errors. Per the SVG
    * specification, if there are any errors in the path definition, the valid portion of the
    * path up until the first error is returned.
    *
    * @param pathDefinition an SVG path element definition string
    * @return an Android {@code Path}
    * @since 1.5
    */
   public static android.graphics.Path  parsePath(String pathDefinition)
   {
      PathDefinition pathDef = ParserHelper.parsePath(new AndroidLogger(), pathDefinition);
      SVGAndroidRenderer.PathConverter  pathConv = new SVGAndroidRenderer.PathConverter(pathDef);
      return pathConv.getPath();
   }


   //===============================================================================

   /**
    * Tells the parser whether to allow the expansion of internal entities.
    * An example of a document containing an internal entities is:
    *
    * {@code
    * <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.0//EN" "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd" [
    *   <!ENTITY hello "Hello World!">
    * ]>
    * <svg>
    *    <text>&hello;</text>
    * </svg>
    * }
    *
    * Entities are useful in some circumstances, but SVG files that use them are quite rare.  Note
    * also that enabling entity expansion makes you vulnerable to the
    * <a href="https://en.wikipedia.org/wiki/Billion_laughs_attack">Billion Laughs Attack</a>
    *
    * Entity expansion is enabled by default.
    *
    * @param enable Set true if you want to enable entity expansion by the parser.
    * @since 1.3
    */
   @SuppressWarnings("unused")
   public static void  setInternalEntitiesEnabled(boolean enable)
   {
      enableInternalEntitiesSingleton = enable;
   }

   /**
    * Indicates whether internal entities were enabled when this SVG was parsed.
    *
    * <p>
    * <em>Note: prior to release 1.5, this was a static method of (@code SVG}.  In 1.5, it was
    * changed to a instance method to coincide with the change making parsing settings thread safe.</em>
    * </p>
    *
    * @return true if internal entity expansion is enabled in the parser
    * @since 1.5
    */
   @SuppressWarnings("unused")
   public boolean  isInternalEntitiesEnabled()
   {
      return enableInternalEntities;
   }


   /**
    * Register an {@link SVGExternalFileResolver} instance that the renderer should use when resolving
    * external references such as images, fonts, and CSS stylesheets.
    *
    * <p>
    * <em>Note: prior to release 1.3, this was an instance method of (@code SVG}.  In 1.3, it was
    * changed to a static method so that users can resolve external references to CSSS files while
    * the SVG is being parsed.</em>
    * </p>
    *
    * @param fileResolver the resolver to use.
    * @since 1.3
    */
   @SuppressWarnings("unused")
   public static void  registerExternalFileResolver(SVGExternalFileResolver fileResolver)
   {
      externalFileResolverSingleton = fileResolver;
   }


   /**
    * De-register the current {@link SVGExternalFileResolver} instance.
    *
    * @since 1.3
    */
   @SuppressWarnings("unused")
   public static void  deregisterExternalFileResolver()
   {
      externalFileResolverSingleton = null;
   }




   /**
    * Get the {@link SVGExternalFileResolver} in effect when this SVG was parsed..
    *
    * @return the current external file resolver instance
    * @since 1.5
    */
   @SuppressWarnings("unused")
   public SVGExternalFileResolver  getExternalFileResolver()
   {
      return externalFileResolver;
   }


   /**
    * Set the DPI (dots-per-inch) value to use when rendering.  The DPI setting is used in the
    * conversion of "physical" units - such an "pt" or "cm" - to pixel values.  The default DPI is 96.
    * <p>
    * You should not normally need to alter the DPI from the default of 96 as recommended by the SVG
    * and CSS specifications.
    *
    * @param dpi the DPI value that the renderer should use.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  setRenderDPI(float dpi)
   {
      this.renderDPI = dpi;
   }


   /**
    * Get the current render DPI setting.
    *
    * @return the DPI value
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public float  getRenderDPI()
   {
      return renderDPI;
   }


   //===============================================================================
   // SVG document rendering to a Picture object (indirect rendering)


   /**
    * Renders this SVG document to a Picture object.
    * <p>
    * An attempt will be made to determine a suitable initial viewport from the contents of the SVG file.
    * If an appropriate viewport can't be determined, a default viewport of 512x512 will be used.
    *
    * @return a Picture object suitable for later rendering using {@code Canvas.drawPicture()}
    */
   @SuppressWarnings("WeakerAccess")
   public Picture  renderToPicture()
   {
      return renderToPicture(null);
   }


   /**
    * Renders this SVG document to a {@link Picture}.
    *
    * @param widthInPixels  the width of the initial viewport
    * @param heightInPixels the height of the initial viewport
    * @return a Picture object suitable for later rendering using {@link Canvas#drawPicture(Picture)}
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public Picture  renderToPicture(int widthInPixels, int heightInPixels)
   {
      return renderToPicture(widthInPixels, heightInPixels, null);
   }



   /**
    * Renders this SVG document to a {@link Picture}.
    *
    * @param renderOptions options that describe how to render this SVG on the Canvas.
    * @return a Picture object suitable for later rendering using {@link Canvas#drawPicture(Picture)}
    * @since 1.3
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public Picture  renderToPicture(RenderOptionsBase renderOptions)
   {
      Box viewBox = (renderOptions != null && renderOptions.hasViewBox()) ? renderOptions.viewBox
                                                                           : rootElement.viewBox;

      // If a viewPort was supplied in the renderOptions, then use its maxX and maxY as the Picture size
      if (renderOptions != null && renderOptions.hasViewPort())
      {
         float w = renderOptions.viewPort.maxX();
         float h = renderOptions.viewPort.maxY();
         return renderToPicture( (int) Math.ceil(w), (int) Math.ceil(h), renderOptions );
      }
      else if (rootElement.width != null && rootElement.width.unit != Unit.percent &&
               rootElement.height != null && rootElement.height.unit != Unit.percent)
      {
         float w = computeFloatValue(rootElement.width, this.renderDPI, renderOptions);
         float h = computeFloatValue(rootElement.height, this.renderDPI, renderOptions);
         return renderToPicture( (int) Math.ceil(w), (int) Math.ceil(h), renderOptions );
      }
      else if (rootElement.width != null && viewBox != null)
      {
         // Width and viewBox supplied, but no height
         // Determine the Picture size and initial viewport. See SVG spec section 7.12.
         float  w = computeFloatValue(rootElement.width, this.renderDPI, renderOptions);
         float  h = w * viewBox.height / viewBox.width;
         return renderToPicture( (int) Math.ceil(w), (int) Math.ceil(h), renderOptions );
      }
      else if (rootElement.height != null && viewBox != null)
      {
         // Height and viewBox supplied, but no width
         float  h = computeFloatValue(rootElement.height, this.renderDPI, renderOptions);
         float  w = h * viewBox.width / viewBox.height;
         return renderToPicture( (int) Math.ceil(w), (int) Math.ceil(h), renderOptions );
      }
      else if (renderOptions == null && viewBox != null)
      {
         float width = viewBox.width;
         float height = viewBox.height;
         // The only reference frame for the picture's size is its viewbox dimensions.
         return renderToPicture((int) width, (int) height, renderOptions);
      }
      else
      {
         return renderToPicture(DEFAULT_PICTURE_WIDTH, DEFAULT_PICTURE_HEIGHT, renderOptions);
      }
   }


   /**
    * Renders this SVG document to a {@link Picture}.
    *
    * @param widthInPixels  the width of the {@code Picture}
    * @param heightInPixels the height of the {@code Picture}
    * @param renderOptions  options that describe how to render this SVG on the Canvas.
    * @return a Picture object suitable for later rendering using {@link Canvas#drawPicture(Picture)}
    * @since 1.3
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public Picture  renderToPicture(int widthInPixels, int heightInPixels, RenderOptionsBase renderOptions)
   {
      Picture  picture = new Picture();
      Canvas   canvas = picture.beginRecording(widthInPixels, heightInPixels);

      if (renderOptions == null || renderOptions.viewPort == null) {
         renderOptions = (renderOptions == null) ? new RenderOptionsBase() : new RenderOptionsBase(renderOptions);
         renderOptions.viewPort(0f, 0f, (float) widthInPixels, (float) heightInPixels);
      }

      SVGAndroidRenderer  renderer = new SVGAndroidRenderer(canvas, this.renderDPI, externalFileResolver);

      renderer.renderDocument(this, renderOptions);

      picture.endRecording();
      return picture;
   }



   /**
    * Renders this SVG document to a {@link Picture} using the specified view defined in the document.
    * <p>
    * A View is an special element in a SVG document that describes a rectangular area in the document.
    * Calling this method with a {@code viewId} will result in the specified view being positioned and scaled
    * to the viewport.  In other words, use {@link #renderToPicture()} to render the whole document, or use this
    * method instead to render just a part of it.
    *
    * @param viewId         the id of a view element in the document that defines which section of the document is to be visible.
    * @param widthInPixels  the width of the initial viewport
    * @param heightInPixels the height of the initial viewport
    * @return a Picture object suitable for later rendering using {@code Canvas.drawPicture()}, or null if the viewId was not found.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public Picture  renderViewToPicture(String viewId, int widthInPixels, int heightInPixels)
   {
      RenderOptions  renderOptions = new RenderOptions();
      renderOptions.view(viewId)
                   .viewPort(0f, 0f, (float) widthInPixels, (float) heightInPixels);


      Picture  picture = new Picture();
      Canvas   canvas = picture.beginRecording(widthInPixels, heightInPixels);

      SVGAndroidRenderer  renderer = new SVGAndroidRenderer(canvas, this.renderDPI, externalFileResolver);

      renderer.renderDocument(this, renderOptions);

      picture.endRecording();
      return picture;
   }


   //===============================================================================
   // SVG document rendering to a canvas object (direct rendering)


   /**
    * Renders this SVG document to a Canvas object.  The full width and height of the canvas
    * will be used as the viewport into which the document will be rendered.
    *
    * @param canvas the canvas to which the document should be rendered.
    * @since 1.3
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  renderToCanvas(Canvas canvas)
   {
      renderToCanvas(canvas, (RenderOptions) null);
   }


   /**
    * Renders this SVG document to a Canvas object.
    *
    * @param canvas   the canvas to which the document should be rendered.
    * @param viewPort the bounds of the area on the canvas you want the SVG rendered, or null for the whole canvas.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  renderToCanvas(Canvas canvas, RectF viewPort)
   {
      RenderOptions  renderOptions = new RenderOptions();

      if (viewPort != null) {
         renderOptions.viewPort(viewPort.left, viewPort.top, viewPort.width(), viewPort.height());
      } else {
         renderOptions.viewPort(0f, 0f, (float) canvas.getWidth(), (float) canvas.getHeight());
      }

      SVGAndroidRenderer  renderer = new SVGAndroidRenderer(canvas, this.renderDPI, externalFileResolver);

      renderer.renderDocument(this, renderOptions);
   }


   /**
    * Renders this SVG document to a Canvas object.
    *
    * @param canvas        the canvas to which the document should be rendered.
    * @param renderOptions options that describe how to render this SVG on the Canvas.
    * @since 1.3
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  renderToCanvas(Canvas canvas, RenderOptions renderOptions)
   {
      if (renderOptions == null)
         renderOptions = new RenderOptions();

      if (!renderOptions.hasViewPort()) {
         renderOptions.viewPort(0f, 0f, (float) canvas.getWidth(), (float) canvas.getHeight());
      }

      SVGAndroidRenderer  renderer = new SVGAndroidRenderer(canvas, this.renderDPI, externalFileResolver);

      renderer.renderDocument(this, renderOptions);
   }


   /**
    * Renders this SVG document to a Canvas using the specified view defined in the document.
    * <p>
    * A View is an special element in a SVG documents that describes a rectangular area in the document.
    * Calling this method with a {@code viewId} will result in the specified view being positioned and scaled
    * to the viewport.  In other words, use {@link #renderToPicture()} to render the whole document, or use this
    * method instead to render just a part of it.
    * <p>
    * If the {@code <view>} could not be found, nothing will be drawn.
    *
    * @param viewId the id of a view element in the document that defines which section of the document is to be visible.
    * @param canvas the canvas to which the document should be rendered.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  renderViewToCanvas(String viewId, Canvas canvas)
   {
      renderToCanvas(canvas, RenderOptions.create().view(viewId));
   }


   /**
    * Renders this SVG document to a Canvas using the specified view defined in the document.
    * <p>
    * A View is an special element in a SVG documents that describes a rectangular area in the document.
    * Calling this method with a {@code viewId} will result in the specified view being positioned and scaled
    * to the viewport.  In other words, use {@link #renderToPicture()} to render the whole document, or use this
    * method instead to render just a part of it.
    * <p>
    * If the {@code <view>} could not be found, nothing will be drawn.
    *
    * @param viewId   the id of a view element in the document that defines which section of the document is to be visible.
    * @param canvas   the canvas to which the document should be rendered.
    * @param viewPort the bounds of the area on the canvas you want the SVG rendered, or null for the whole canvas.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  renderViewToCanvas(String viewId, Canvas canvas, RectF viewPort)
   {
      RenderOptions  renderOptions = RenderOptions.create().view(viewId);

      if (viewPort != null) {
         renderOptions.viewPort(viewPort.left, viewPort.top, viewPort.width(), viewPort.height());
      }

      renderToCanvas(canvas, renderOptions);
   }


   //===============================================================================
   // Other document utility API functions


   @Override
   @SuppressWarnings({"WeakerAccess", "unused"})
   public String getDocumentTitle()
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      return title;
   }


   @Override
   @SuppressWarnings({"WeakerAccess", "unused"})
   public String getDocumentDescription()
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      return desc;
   }


   @Override
   @SuppressWarnings({"WeakerAccess", "unused"})
   public String getDocumentSVGVersion()
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      return rootElement.version;
   }


   /**
    * Returns a list of ids for all {@code <view>} elements in this SVG document.
    * <p>
    * The returned view ids could be used when calling and of the {@code renderViewToX()} methods.
    *
    * @return the list of id strings.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public Set<String> getViewList()
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      List<SvgObject>  viewElems = getElementsByTagName(View.NODE_NAME);

      Set<String>  viewIds = new HashSet<>(viewElems.size());
      for (SvgObject elem: viewElems)
      {
         View  view = (View) elem;
         if (view.id != null)
            viewIds.add(view.id);
         else
            Log.w("AndroidSVG", "getViewList(): found a <view> without an id attribute");
      }
      return viewIds;
   }


   /**
    * Returns the width of the document as specified in the SVG file.
    * <p>
    * If the width in the document is specified in pixels, that value will be returned.
    * If the value is listed with a physical unit such as "cm", then the current
    * {@code RenderDPI} value will be used to convert that value to pixels. If the width
    * is missing, or in a form which can't be converted to pixels, such as "100%" for
    * example, -1 will be returned.
    *
    * @return the width in pixels, or -1 if there is no width available.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public float  getDocumentWidth(RenderOptionsBase options)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      return getDocumentDimensions(this.renderDPI, options).width;
   }


   /**
    * Change the width of the document by altering the "width" attribute
    * of the root {@code <svg>} element.
    *
    * @param pixels The new value of width in pixels.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  setDocumentWidth(float pixels)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      this.rootElement.width = new Length(pixels);
   }


   /**
    * Change the width of the document by altering the "width" attribute
    * of the root {@code <svg>} element.
    *
    * @param value A valid SVG 'length' attribute, such as "100px" or "10cm".
    * @throws SVGParseException        if {@code value} cannot be parsed successfully.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  setDocumentWidth(String value) throws SVGParseException
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      this.rootElement.width = ParserHelper.parseLength(value);
   }


   /**
    * Returns the height of the document as specified in the SVG file.
    * <p>
    * If the height in the document is specified in pixels, that value will be returned.
    * If the value is listed with a physical unit such as "cm", then the current
    * {@code RenderDPI} value will be used to convert that value to pixels. If the height
    * is missing, or in a form which can't be converted to pixels, such as "100%" for
    * example, -1 will be returned.
    *
    * @return the height in pixels, or -1 if there is no height available.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public float  getDocumentHeight(RenderOptionsBase options)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      return getDocumentDimensions(this.renderDPI, options).height;
   }


   /**
    * Change the height of the document by altering the "height" attribute
    * of the root {@code <svg>} element.
    *
    * @param pixels The new value of height in pixels.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  setDocumentHeight(float pixels)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      this.rootElement.height = new Length(pixels);
   }


   /**
    * Change the height of the document by altering the "height" attribute
    * of the root {@code <svg>} element.
    *
    * @param value A valid SVG 'length' attribute, such as "100px" or "10cm".
    * @throws SVGParseException        if {@code value} cannot be parsed successfully.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  setDocumentHeight(String value) throws SVGParseException
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      this.rootElement.height = ParserHelper.parseLength(value);
   }

   public float getVerticalAlignment(RenderOptionsBase options)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");
      return computeFloatValue(this.rootElement.style.verticalAlignment, this.renderDPI, options);
   }

   /**
    * Change the document view box by altering the "viewBox" attribute
    * of the root {@code <svg>} element.
    * <p>
    * The viewBox generally describes the bounding box dimensions of the
    * document contents.  A valid viewBox is necessary if you want the
    * document scaled to fit the canvas or viewport the document is to be
    * rendered into.
    * <p>
    * By setting a viewBox that describes only a portion of the document,
    * you can reproduce the effect of image sprites.
    *
    * @param minX   the left coordinate of the viewBox in pixels
    * @param minY   the top coordinate of the viewBox in pixels.
    * @param width  the width of the viewBox in pixels
    * @param height the height of the viewBox in pixels
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  setDocumentViewBox(float minX, float minY, float width, float height)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      this.rootElement.viewBox = new Box(minX, minY, width, height);
   }


   /**
    * Returns the viewBox attribute of the current SVG document.
    *
    * @return the document's viewBox attribute as a {@code android.graphics.RectF} object, or null if not set.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public RectF  getDocumentViewBox()
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      if (this.rootElement.viewBox == null)
         return null;

      return new RectF(
              this.rootElement.viewBox.minX,
              this.rootElement.viewBox.minY,
              this.rootElement.viewBox.maxX(),
              this.rootElement.viewBox.maxY()
      );
   }


   /**
    * Change the document positioning by altering the "preserveAspectRatio"
    * attribute of the root {@code <svg>} element.  See the
    * documentation for {@link PreserveAspectRatio} for more information
    * on how positioning works.
    *
    * @param preserveAspectRatio the new {@code preserveAspectRatio} setting for the root {@code <svg>} element.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public void  setDocumentPreserveAspectRatio(PreserveAspectRatio preserveAspectRatio)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      this.rootElement.preserveAspectRatio = preserveAspectRatio;
   }


   /**
    * Return the "preserveAspectRatio" attribute of the root {@code <svg>}
    * element in the form of an {@link PreserveAspectRatio} object.
    *
    * @return the preserveAspectRatio setting of the document's root {@code <svg>} element.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public PreserveAspectRatio  getDocumentPreserveAspectRatio()
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      if (this.rootElement.preserveAspectRatio == null)
         return null;

      return this.rootElement.preserveAspectRatio;
   }


   /**
    * Returns the aspect ratio of the document as a width/height fraction.
    * <p>
    * If the width or height of the document are listed with a physical unit such as "cm",
    * then the current {@code renderDPI} setting will be used to convert that value to pixels.
    * <p>
    * If the width or height cannot be determined, -1 will be returned.
    *
    * @return the aspect ratio as a width/height fraction, or -1 if the ratio cannot be determined.
    * @throws IllegalArgumentException if there is no current SVG document loaded.
    */
   @SuppressWarnings({"WeakerAccess", "unused"})
   public float  getDocumentAspectRatio(RenderOptionsBase options)
   {
      if (this.rootElement == null)
         throw new IllegalArgumentException("SVG document is empty");

      Length  w = this.rootElement.width;
      Length  h = this.rootElement.height;

      // If width and height are both specified and are not percentages, aspect ratio is calculated from these (SVG1.1 sect 7.12)
      if (w != null && h != null && w.unit!= Unit.percent && h.unit!= Unit.percent)
      {
         if (w.isZero() || h.isZero())
            return -1f;
         return computeFloatValue(w, this.renderDPI, options) / computeFloatValue(h, this.renderDPI, options);
      }

      // Otherwise, get the ratio from the viewBox
      if (this.rootElement.viewBox != null && this.rootElement.viewBox.width != 0f && this.rootElement.viewBox.height != 0f) {
         return this.rootElement.viewBox.width / this.rootElement.viewBox.height;
      }

      // Could not determine aspect ratio
      return -1f;
   }



   //===============================================================================

   protected static SVGParser createParser()
   {
      SVGParser.Factory factory = new SVGParserFactoryImpl();
      SvgStructure.Factory structureFactory = (enableInternalEntities, cssResolver) ->
              new SvgRenderingBase(enableInternalEntities, (SVGExternalFileResolver) cssResolver);
      Logger logger = new AndroidLogger();
      return factory.createParser(structureFactory, new AutomaticAndroidXmlParser(logger), logger)
              .setInternalEntitiesEnabled(enableInternalEntitiesSingleton)
              .setExternalFileResolver(externalFileResolverSingleton);
   }


   //===============================================================================


   @Override
   public Svg getRootElement()
   {
      return rootElement;
   }

   @Override
   public void setRootElement(Svg rootElement)
   {
      this.rootElement = rootElement;
   }


   SvgObject  resolveIRI(String iri)
   {
      if (iri == null)
         return null;

      iri = cssQuotedString(iri);
      if (iri.length() > 1 && iri.startsWith("#"))
      {
         return getElementById(iri.substring(1));
      }
      return null;
   }


   private String  cssQuotedString(String str)
   {
      if (str.startsWith("\"") && str.endsWith("\""))
      {
         // Remove quotes and replace escaped double-quote
         str = str.substring(1, str.length()-1).replace("\\\"", "\"");
      }
      else if (str.startsWith("'") && str.endsWith("'"))
      {
         // Remove quotes and replace escaped single-quote
         str = str.substring(1, str.length()-1).replace("\\'", "'");
      }
      // Remove escaped newline. Replace escape seq representing newline
      return str.replace("\\\n", "").replace("\\A", "\n");
   }

   private Box  getDocumentDimensions(float dpi, RenderOptionsBase options)
   {
      Length  w = this.rootElement.width;
      Length  h = this.rootElement.height;
      
      if (w == null || w.isZero() || w.unit== Unit.percent)
         return new Box(-1,-1,-1,-1);

      if (options == null && (w.unit== Unit.em || w.unit== Unit.ex))
         return new Box(-1,-1,-1,-1);

      float  wOut = computeFloatValue(w, dpi, options);
      float  hOut;

      if (h != null) {
         if (h.isZero() || h.unit== Unit.percent) {
            return new Box(-1,-1,-1,-1);
         }
         if (options == null && (h.unit== Unit.em || h.unit== Unit.ex)) {
            return new Box(-1,-1,-1,-1);
         }
         hOut = computeFloatValue(h, dpi, options);
      } else {
         // height is not specified. SVG spec says this is okay. If there is a viewBox, we use
         // that to calculate the height. Otherwise we set height equal to width.
         if (this.rootElement.viewBox != null) {
            hOut = (wOut * this.rootElement.viewBox.height) / this.rootElement.viewBox.width;
         } else {
            hOut = wOut;
         }
      }
      return new Box(0,0, wOut,hOut);
   }


   //===============================================================================
   // CSS support methods

   @Override
   public void addCSSRules(Ruleset ruleset)
   {
      this.cssRules.addAll(ruleset);
   }

   List<Rule>  getCSSRules()
   {
      return this.cssRules.getRules();
   }


   boolean  hasCSSRules()
   {
      return !this.cssRules.isEmpty();
   }


   void  clearRenderCSSRules()
   {
      this.cssRules.removeFromSource(Source.RenderOptions);
   }


   //===============================================================================
   // Object sub-types used in the SVG object tree


   //===============================================================================
   // The objects in the SVG object tree
   //===============================================================================


   //===============================================================================
   // Protected setters for internal use


   @Override
   public void setTitle(String title)
   {
      this.title = title;
   }

   @Override
   public void setDesc(String desc)
   {
      this.desc = desc;
   }


   //===============================================================================
   // Path definition


   SvgElementBase  getElementById(String id)
   {
      if (id == null || id.length() == 0)
         return null;
      if (id.equals(rootElement.id))
         return rootElement;

      if (idToElementMap.containsKey(id))
         return idToElementMap.get(id);

      // Search the object tree for a node with id property that matches 'id'
      SvgElementBase  result = getElementById(rootElement, id);
      idToElementMap.put(id, result);
      return result;
   }


   private SvgElementBase  getElementById(SvgContainer obj, String id)
   {
      SvgElementBase  elem = (SvgElementBase) obj;
      if (id.equals(elem.id))
         return elem;
      for (SvgObject child: obj.getChildren())
      {
         if (!(child instanceof SvgElementBase))
            continue;
         SvgElementBase  childElem = (SvgElementBase) child;
         if (id.equals(childElem.id))
            return childElem;
         if (child instanceof SvgContainer)
         {
            SvgElementBase  found = getElementById((SvgContainer) child, id);
            if (found != null)
               return found;
         }
      }
      return null;
   }


   private List<SvgObject>  getElementsByTagName(String nodeName)
   {
      List<SvgObject>  result = new ArrayList<>();

       // Search the object tree for nodes with the give element class
      getElementsByTagName(result, rootElement, nodeName);
      return result;
   }


   private void  getElementsByTagName(List<SvgObject> result, SvgObject obj, String nodeName)
   {

      if (obj.getNodeName().equals(nodeName))
         result.add(obj);

      if (obj instanceof SvgContainer)
      {
         for (SvgObject child: ((SvgContainer) obj).getChildren())
            getElementsByTagName(result, child, nodeName);
      }
   }

   private static float computeFloatValue(Length length, float dpi, RenderOptionsBase renderOptions) {
      TextPaint textPaint = null;
      if (renderOptions != null) {
         textPaint = renderOptions.textPaint;
      }
      return SVGAndroidRenderer.computeFloatValue(length, dpi, textPaint);
   }
}
