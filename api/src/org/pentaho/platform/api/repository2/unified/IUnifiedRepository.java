package org.pentaho.platform.api.repository2.unified;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Entry point into the unified repository. The finest grained object that can be read and written to this repository
 * is a {@link RepositoryFile}.
 * 
 * @author mlowery
 */
public interface IUnifiedRepository {

  /**
   * Gets file. Use this method to test for file existence too.
   * 
   * @param path path to file
   * @return file or {@code null} if the file does not exist or access is denied
   */
  RepositoryFile getFile(final String path);

  /**
   * Gets a tree rooted at path.
   * 
   * @param path path to file
   * @param depth 0 fetches just file at path; positive integer n fetches node at path plus n levels of children; 
   * negative integer fetches all children
   * @param filter filter may be a full name or a partial name with one or more wildcard characters ("*"), or a 
   * disjunction (using the "|" character to represent logical OR) of these; filter does not apply to root node
   * @return file or {@code null} if the file does not exist or access is denied
   */
  RepositoryFileTree getTree(final String path, final int depth, final String filter);

  /**
   * Gets file as it was at the given version.
   * 
   * @param fileId file id
   * @param versionId version id
   * @return file at version
   */
  RepositoryFile getFileAtVersion(final Serializable fileId, final Serializable versionId);

  /**
   * Gets file. Use this method to test for file existence too.
   * 
   * @param fileId file id
   * @return file or {@code null} if the file does not exist or access is denied
   */
  RepositoryFile getFileById(final Serializable fileId);

  /**
   * Same as {@link #getFile(String)} except that if {@code loadMaps} is {@code true}, the maps for localized strings 
   * will be loaded as well. (Normally these are not loaded.) Use {@code true} in editing tools that can show the maps
   * for editing purposes.
   * 
   * @param path path to file
   * @param loadLocaleMaps {@code true} to load localized string maps
   * @return file or {@code null} if the file does not exist or access is denied
   */
  RepositoryFile getFile(final String path, final boolean loadLocaleMaps);

  /**
   * Same as {@link #getFile(String)} except that if {@code loadMaps} is {@code true}, the maps for localized strings 
   * will be loaded as well. (Normally these are not loaded.) Use {@code true} in editing tools that can show the maps
   * for editing purposes.
   * 
   * @param fileId file id
   * @param loadLocaleMaps {@code true} to load localized string maps
   * @return file or {@code null} if the file does not exist or access is denied
   */
  RepositoryFile getFileById(final Serializable fileId, final boolean loadLocaleMaps);

  /**
   * Gets data at base version for read.
   * 
   * @param fileId file id
   * @param dataClass class that implements {@link IRepositoryFileData}
   * @return data
   */
  <T extends IRepositoryFileData> T getDataForRead(final Serializable fileId, final Class<T> dataClass);

  /**
   * Gets data at given version for read.
   * 
   * @param fileId file id
   * @param versionId version id
   * @param dataClass class that implements {@link IRepositoryFileData}
   * @return data
   */
  <T extends IRepositoryFileData> T getDataAtVersionForRead(final Serializable fileId, final Serializable versionId,
      final Class<T> dataClass);

  /**
   * Gets data at base version for execute.
   * 
   * @param fileId file id
   * @param dataClass class that implements {@link IRepositoryFileData}
   * @return data
   */
  <T extends IRepositoryFileData> T getDataForExecute(final Serializable fileId, final Class<T> dataClass);

  /**
   * Gets data at given version for read.
   * 
   * @param fileId file id
   * @param versionId version id
   * @param dataClass class that implements {@link IRepositoryFileData}
   * @return data
   */
  <T extends IRepositoryFileData> T getDataAtVersionForExecute(final Serializable fileId, final Serializable versionId,
      final Class<T> dataClass);
  
  /**
   * Gets the data for multiple {@link RepositoryFile}s for read.  Each {@link RepositoryFile} may or may not contain a 
   * version number.  If a version number is omitted it is assumed the latest data for the {@link RepositoryFile} 
   * is being requested.
   *  
   * @param <T> Type of {@link IRepositoryFileData}
   * @param files Repository files to fetch data for. Only {@link RepositoryFile#getId()} and {@link RepositoryFile#getVersionId()}
   *              are used to identify {@link IRepositoryFileData} objects to return.
   * @param dataClass class that implements {@link IRepositoryFileData}
   * @return data
   */
  <T extends IRepositoryFileData> java.util.List<T> getDataForReadInBatch(final List<RepositoryFile> files, final Class<T> dataClass);

  /**
   * Gets the data for multiple {@link RepositoryFile}s for execute.  Each {@link RepositoryFile} may or may not contain a 
   * version number.  If a version number is omitted it is assumed the latest data for the {@link RepositoryFile} 
   * is being requested.
   *  
   * @param <T> Type of {@link IRepositoryFileData}
   * @param files Repository files to fetch data for. Only {@link RepositoryFile#getId()} and {@link RepositoryFile#getVersionId()}
   *              are used to identify {@link IRepositoryFileData} objects to return.
   * @param dataClass class that implements {@link IRepositoryFileData}
   * @return data
   */
  <T extends IRepositoryFileData> java.util.List<T> getDataForExecuteInBatch(final List<RepositoryFile> files, final Class<T> dataClass);

  /**
   * Creates a file.
   * 
   * @param parentFolderId parent folder id
   * @param file file to create
   * @param data file data
   * @param versionMessage optional version comment to be applied to parentFolder
   * @return file that is equal to given file except with id populated
   */
  RepositoryFile createFile(final Serializable parentFolderId, final RepositoryFile file,
      final IRepositoryFileData data, final String versionMessage);

  /**
   * Creates a file.
   * 
   * @param parentFolderId parent folder id
   * @param file file to create
   * @param data file data
   * @param acl file acl
   * @param versionMessage optional version comment to be applied to parentFolder
   * @return file that is equal to given file except with id populated
   */
  RepositoryFile createFile(final Serializable parentFolderId, final RepositoryFile file,
      final IRepositoryFileData data, final RepositoryFileAcl acl, final String versionMessage);

  /**
   * Creates a folder.
   * 
   * @param parentFolderId parent folder id
   * @param file file to create
   * @param versionMessage optional version comment to be applied to parentFolder
   * @return file that is equal to given file except with id populated
   */
  RepositoryFile createFolder(final Serializable parentFolderId, final RepositoryFile file, final String versionMessage);

  /**
   * Creates a folder.
   * 
   * @param parentFolderId parent folder id
   * @param file file to create
   * @param acl file acl
   * @param versionMessage optional version comment to be applied to parentFolder
   * @return file that is equal to given file except with id populated
   */
  RepositoryFile createFolder(final Serializable parentFolderId, final RepositoryFile file,
      final RepositoryFileAcl acl, final String versionMessage);

  /**
   * Returns the children of this folder.
   * 
   * @param folderId id of folder whose children to fetch
   * @return list of children (never {@code null})
   */
  List<RepositoryFile> getChildren(final Serializable folderId);

  /**
   * Returns the children of this folder that match the specified filter.
   * 
   * @param folderId id of folder whose children to fetch
   * @param filter filter may be a full name or a partial name with one or more wildcard characters ("*"), or a 
   * disjunction (using the "|" character to represent logical OR) of these
   * @return list of children (never {@code null})
   */
  List<RepositoryFile> getChildren(final Serializable folderId, final String filter);

  /**
   * Updates a file and/or the data of a file.
   * 
   * @param file updated file (not a folder); must have non-null id
   * @param data updated data
   * @param versionMessage (optional) version comment
   * @return updated file (possibly with new version number)
   */
  RepositoryFile updateFile(final RepositoryFile file, final IRepositoryFileData data, final String versionMessage);

  /**
   * Deletes a file.
   * 
   * @param fileId file id
   * @param permanent if {@code true}, once file is deleted, it cannot be undeleted
   * @param versionMessage optional version comment
   */
  void deleteFile(final Serializable fileId, final boolean permanent, final String versionMessage);

  /**
   * Deletes a file in a recoverable manner.
   * 
   * @param fileId file id
   * @param versionMessage optional version comment
   */
  void deleteFile(final Serializable fileId, final String versionMessage);

  /**
   * Moves and/or renames file. Folders are recursive. Throws exception on collision (merging does not occur).
   * 
   * @param fileId id of file or folder to move and/or rename
   * @param destAbsPath path to destination; if only moving then destAbsPath will be an existing folder
   * @param versionMessage optional version comment to be applied to source and destination parent folders
   */
  void moveFile(final Serializable fileId, final String destAbsPath, final String versionMessage);

  /**
   * Copies file. Folders are recursive. Throws exception on collision (merging does not occur).
   * 
   * @param fileId id of file or folder to copy
   * @param destAbsPath path to destination; if only copying (without name change) then destAbsPath will be an existing 
   * folder
   * @param versionMessage optional version comment to be applied to destination parent folder
   */
  void copyFile(final Serializable fileId, final String destAbsPath, final String versionMessage);

  // ~ Undelete methods ================================================================================================

  /**
   * Recovers a deleted file if it was not permanently deleted. File is recovered to its original folder.
   * 
   * @param fileId deleted file id
   * @param versionMessage optional version comment to be applied to original parent folder
   */
  void undeleteFile(final Serializable fileId, final String versionMessage);

  /**
   * Gets all deleted files for the current user in this folder.
   * 
   * @param origParentFolderPath path to original parent folder
   * @return list of deleted files
   */
  List<RepositoryFile> getDeletedFiles(final String origParentFolderPath);

  /**
   * Gets all deleted files for the current user in this folder.
   * 
   * @param origParentFolderPath path to original parent folder
   * @param filter filter may be a full name or a partial name with one or more wildcard characters ("*"), or a 
   * disjunction (using the "|" character to represent logical OR) of these
   * @return list of deleted files
   */
  List<RepositoryFile> getDeletedFiles(final String origParentFolderPath, final String filter);

  /**
   * Gets all deleted files for the current user. This is the "recycle bin" view.
   * 
   * @return list of deleted files
   */
  List<RepositoryFile> getDeletedFiles();

  // ~ Lock methods ====================================================================================================

  /**
   * Returns {@code true} if the current user can unlock the file. This might be a function of access control.
   * 
   * @param fileId file id
   * @return {@code true} if the current user can unlock the file
   */
  boolean canUnlockFile(final Serializable fileId);

  /**
   * Locks a file.
   * 
   * @param fileId file id
   * @param lock message
   */
  void lockFile(final Serializable fileId, final String message);

  /**
   * Unlocks a file.
   * 
   * @param fileId file id
   */
  void unlockFile(final Serializable fileId);

  // ~ Access read/write methods =======================================================================================

  /**
   * Returns ACL for file.
   * 
   * @param fileId file id
   * @return access control list
   */
  RepositoryFileAcl getAcl(final Serializable fileId);

  /**
   * Updates an ACL.
   * 
   * @param acl ACL to set; must have non-null id
   * @return updated ACL as it would be if calling {@link #getAcl(Serializable)}
   */
  RepositoryFileAcl updateAcl(final RepositoryFileAcl acl);

  /**
   * Returns {@code true} if user has all permissions given. Note that {@code false} is returned when the path does not 
   * exist.
   * 
   * @param path path to file or folder
   * @param permissions permissions to check
   * @return {@code true} if user has all permissions given
   */
  boolean hasAccess(final String path, final EnumSet<RepositoryFilePermission> permissions);

  /**
   * Returns the list of access control entries (ACEs) that will be used to make an access control decision. This method
   * is equivalent to {@code getEffectiveAces(fileId, false)}.
   * 
   * @param fileId file id
   * @return list of ACEs
   */
  List<RepositoryFileAce> getEffectiveAces(final Serializable fileId);

  /**
   * Returns the list of access control entries (ACEs) that will be used to make an access control decision. This method
   * is equivalent to {@code getEffectiveAces(get_parent_id(fileId))}.  Note that {@code get_parent_id} is not a real 
   * method.
   * 
   * @param fileId file id
   * @param forceEntriesInheriting {@code true} to treat ACL as if {@code isEntriesInheriting} was true; this avoids 
   * having the caller fetch the parent of ACL belonging to file with {@code fileId}; no change is persisted to the ACL
   * @return list of ACEs
   */
  List<RepositoryFileAce> getEffectiveAces(final Serializable fileId, final boolean forceEntriesInheriting);

  // ~ Version methods =================================================================================================

  /**
   * Returns a version summary for the given file id and version id.
   * 
   * @param fileId file id
   * @param versionId version id (if {@code null}, returns the last version)
   * @return version summary
   */
  VersionSummary getVersionSummary(Serializable fileId, Serializable versionId);

  /**
   * Returns a version summary for every {@link RepositoryFile} provided.  Each {@link RepositoryFile} may or may not contain a 
   * version number.  If a version number is omitted it is assumed the latest version for the {@link RepositoryFile} 
   * is being requested.
   * 
   * @param files Repository files to fetch version summaries for. Only {@link RepositoryFile#getId()} and {@link RepositoryFile#getVersionId()}
   *              are used to identify {@link VersionSummary} objects to return.
   * @return version summary for every file provided
   */
  List<VersionSummary> getVersionSummaryInBatch(final List<RepositoryFile> files);
  
  /**
   * Returns a list of version summary instances. The first version in the list is the root version. The last version
   * in the list is the base version. Branching and merging are not supported so this is a simple list.
   * 
   * @param fileId file id
   * @return list of version summaries (never {@code null})
   */
  List<VersionSummary> getVersionSummaries(final Serializable fileId);

  /**
   * Permanently deletes a specific version of a file. The version is removed from the
   * version history of the given file.
   * 
   * @param fileId file id
   * @param versionId version id (MUST not be null)
   */
  void deleteFileAtVersion(final Serializable fileId, final Serializable versionId);

  /**
   * Makes a file, as it was at the given version, the latest version. Result should be the same as if the user had 
   * called {@link #updateFile(RepositoryFile, IRepositoryFileData, String)} with a file and data that matched the state
   * of the file and data at the given version.
   * 
   * @param fileId file id
   * @param versionId version id
   * @param versionMessage optional version comment
   */
  void restoreFileAtVersion(final Serializable fileId, final Serializable versionId, final String versionMessage);

  /**
   * Get a list of {@link RepositoryFile}s that reference the RepositoryFile identified
   * by fileId. 
   * 
   * @param fileId file id
   * @return list of repository files that reference the fileId repository file 
   */
  List<RepositoryFile> getReferrers(final Serializable fileId);
 
  // ~ Metadata methods =================================================================================================

  /**
   * Sets a metadata object for the given fileid
   * 
   * @param fileId file id
   * @param metadataMap Map of properties to apply to this file.
   */
  void setFileMetadata(final Serializable fileId, final Map<String, Serializable> metadataMap);
  
  /**
   * Gets a metadata for the given fileid
   * 
   * @param fileId file id
   * @return Map<String, Serializable> of all the metadata for this file
   */
  Map<String, Serializable> getFileMetadata(final Serializable fileId);
}