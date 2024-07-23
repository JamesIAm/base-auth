import * as toolkitRaw from "@reduxjs/toolkit"
const { buildCreateSlice, asyncThunkCreator } = ((toolkitRaw as any).default ??
  toolkitRaw) as typeof toolkitRaw

// `buildCreateSlice` allows us to create a slice with async thunks.
export const createAppSlice = buildCreateSlice({
  creators: { asyncThunk: asyncThunkCreator },
})
