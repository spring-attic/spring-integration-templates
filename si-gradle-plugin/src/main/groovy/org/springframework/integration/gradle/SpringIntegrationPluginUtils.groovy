package org.springframework.integration.gradle

/**
 * Created by IntelliJ IDEA.
 * User: ghillert
 * Date: 12/15/11
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
class SpringIntegrationPluginUtils {

    public static void retrieveClassResource(String inputClassPathResource, String outputFileLocation) {

        println("Copy resource from '" + inputClassPathResource + "' "
              + "to '" + outputFileLocation + "'.");

        InputStream is = SpringIntegrationPluginUtils.getResourceAsStream(inputClassPathResource)

        OutputStream out = new FileOutputStream(outputFileLocation);

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = is.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        is.close();
        out.flush();
        out.close();

    }

    public static boolean deleteNonEmptyDirectory(File directory) {

        if (directory.isDirectory()) {
            for (File child : directory.listFiles()) {
                deleteNonEmptyDirectory(child);
            }
        }

        directory.delete()

    }

}
