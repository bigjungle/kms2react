import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaCatSdmSuffix, defaultValue } from 'app/shared/model/para-cat-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARACATS: 'paraCat/SEARCH_PARACATS',
  FETCH_PARACAT_LIST: 'paraCat/FETCH_PARACAT_LIST',
  FETCH_PARACAT: 'paraCat/FETCH_PARACAT',
  CREATE_PARACAT: 'paraCat/CREATE_PARACAT',
  UPDATE_PARACAT: 'paraCat/UPDATE_PARACAT',
  DELETE_PARACAT: 'paraCat/DELETE_PARACAT',
  SET_BLOB: 'paraCat/SET_BLOB',
  RESET: 'paraCat/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaCatSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaCatSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaCatSdmSuffixState = initialState, action): ParaCatSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARACATS):
    case REQUEST(ACTION_TYPES.FETCH_PARACAT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARACAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARACAT):
    case REQUEST(ACTION_TYPES.UPDATE_PARACAT):
    case REQUEST(ACTION_TYPES.DELETE_PARACAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARACATS):
    case FAILURE(ACTION_TYPES.FETCH_PARACAT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARACAT):
    case FAILURE(ACTION_TYPES.CREATE_PARACAT):
    case FAILURE(ACTION_TYPES.UPDATE_PARACAT):
    case FAILURE(ACTION_TYPES.DELETE_PARACAT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARACATS):
    case SUCCESS(ACTION_TYPES.FETCH_PARACAT_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARACAT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARACAT):
    case SUCCESS(ACTION_TYPES.UPDATE_PARACAT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARACAT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-cats';
const apiSearchUrl = 'api/_search/para-cats';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaCatSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARACATS,
  payload: axios.get<IParaCatSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaCatSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARACAT_LIST,
    payload: axios.get<IParaCatSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaCatSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARACAT,
    payload: axios.get<IParaCatSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaCatSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARACAT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaCatSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARACAT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaCatSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARACAT,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
