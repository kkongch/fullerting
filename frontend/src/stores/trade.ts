import { atom } from "jotai";

export const imageFilesAtom = atom<File[]>([]);
export const selectedDiaryIdAtom = atom<null | string>(null);
